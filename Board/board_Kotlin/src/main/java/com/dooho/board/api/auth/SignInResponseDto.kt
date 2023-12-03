package com.dooho.board.api.auth

import com.dooho.board.api.user.UserEntity

data class SignInResponseDto (
    val token : String? = null,
    val tokenExprTime : Int? = null,
    val refreshToken : String? = null,
    val refreshTokenExprTime : Int? = null,
    val user : UserEntity? = null
)