package com.dooho.board.api.auth

data class RefreshResponseDto(
    var token: String? = null,
    var tokenExprTime: Int? = null
)
