package com.dooho.board.api.auth

import com.dooho.board.api.ResponseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController @Autowired constructor(private val authService: AuthService) {
    @PostMapping("/signUp")
    fun signUp(@RequestBody requestBody: SignUpDto?): ResponseDto<String?> {
        return authService.signUp(requestBody)
    }

    @PostMapping("/signIn")
    fun signIn(@RequestBody requestBody: SignInDto?): ResponseDto<SignInResponseDto?> {
        return authService.signIn(requestBody)
    }

    @PostMapping("/getAccess")
    fun getAccess(@RequestBody refreshToken: String?): ResponseDto<RefreshResponseDto?> {
        return authService.getAccess(refreshToken)
    }
}