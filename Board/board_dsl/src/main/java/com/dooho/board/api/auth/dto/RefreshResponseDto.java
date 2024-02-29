package com.dooho.board.api.auth.dto;


public record RefreshResponseDto(
        String token,
        Integer tokenExprTime
) {
    public static RefreshResponseDto of(String token, Integer tokenExprTime) {
        return new RefreshResponseDto(token, tokenExprTime);

    }

}
