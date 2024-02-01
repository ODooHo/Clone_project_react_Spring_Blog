package com.dooho.board.api.auth.dto;

import com.dooho.board.api.user.UserEntity;


public record SignInResponseDto(
        String token,
        Integer tokenExprTime,
        String refreshToken,
        Integer refreshExprTime,
        UserEntity user
) {
}
