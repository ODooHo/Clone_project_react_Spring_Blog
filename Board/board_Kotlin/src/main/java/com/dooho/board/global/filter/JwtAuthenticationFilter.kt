package com.dooho.board.global.filter

import com.dooho.board.global.security.TokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JwtAuthenticationFilter @Autowired constructor(  //request가 들어왔을 떄 request Header의 authorization 필드의 bearer token값을 가져옴
    // 가져온 토큰을 검증하고 검증 결과를 securityContext에 추가
    private val tokenProvider: TokenProvider
) : OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val token = parseBearerToken(request)
            if (token != null && !token.equals("null", ignoreCase = true)) {
                // 토큰을 검증하여 payload의 userEmail 가져옴
                var userEmail = tokenProvider.validate(token)

                // 만료된 AccessToken이라면 Refresh Token을 이용하여 새로운 AccessToken 발급
                if (userEmail == null) {
                    val refreshToken = request.getHeader("refreshToken")
                    if (refreshToken != null) {
                        val newAccessToken = tokenProvider.createAccessTokenFromRefreshToken(refreshToken)
                        if (newAccessToken != null) {
                            userEmail = tokenProvider.validate(newAccessToken)
                            // 새로 발급한 AccessToken으로 SecurityContext를 업데이트
                            val authentication: AbstractAuthenticationToken =
                                UsernamePasswordAuthenticationToken(userEmail, null, AuthorityUtils.NO_AUTHORITIES)
                            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                            val securityContext = SecurityContextHolder.createEmptyContext()
                            securityContext.authentication = authentication
                            SecurityContextHolder.setContext(securityContext)

                            // 새로운 AccessToken을 응답 헤더에 추가
                            response.addHeader("newAccessToken", newAccessToken)
                        }
                    }
                } else {
                    // 만료되지 않은 AccessToken이라면 SecurityContext에 추가
                    val authentication: AbstractAuthenticationToken =
                        UsernamePasswordAuthenticationToken(userEmail, null, AuthorityUtils.NO_AUTHORITIES)
                    authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                    val securityContext = SecurityContextHolder.createEmptyContext()
                    securityContext.authentication = authentication
                    SecurityContextHolder.setContext(securityContext)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        filterChain.doFilter(request, response)
    }

    // request Header의 authorization 필드의 bearer token값을 가져오는 메서드
    private fun parseBearerToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }
}