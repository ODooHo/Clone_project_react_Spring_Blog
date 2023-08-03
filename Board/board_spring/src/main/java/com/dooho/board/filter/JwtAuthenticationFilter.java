package com.dooho.board.filter;

import com.dooho.board.security.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    //request가 들어왔을 떄 request Header의 authorization 필드의 bearer token값을 가져옴
    // 가져온 토큰을 검증하고 검증 결과를 securityContext에 추가


   private final TokenProvider tokenProvider;

    @Autowired
    public JwtAuthenticationFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
            String token = parseBearerToken(request);

            if(token != null && !token.equalsIgnoreCase("null")){
                //토큰을 검증하여 payload의 userEmail 가져옴
                String userEmail = tokenProvider.validate(token);

                //security context에 추가할 객체
                AbstractAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userEmail,null, AuthorityUtils.NO_AUTHORITIES);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //security context에 AbstractAuthenticationToken 객체를 추가해서 해당
                // thread가 지속적으로 인증 정보를 가질 수 있도록 해줌
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);
            }
        } catch (Exception e){
                e.printStackTrace();
            }

        filterChain.doFilter(request, response);
    }

    // request Header의 authorization 필드의 bearer token값을 가져오는 메서드
    private String parseBearerToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }

        return null;
    }
}
