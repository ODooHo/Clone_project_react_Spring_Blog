package com.dooho.board.api.auth

import jakarta.validation.constraints.NotBlank

data class SignInDto(
    @field:NotBlank val userEmail: String? = null,
    @field:NotBlank val userPassword: String? = null
)