package com.dooho.board.api.auth.dto;


public record RefreshResponseDto(
        String token,
        Integer tokenExprTime
) {

}
