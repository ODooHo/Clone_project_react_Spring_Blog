package com.dooho.board.api.user.dto;

public record PatchUserDto(
        String userNickname,
        String userProfile
) {
}
