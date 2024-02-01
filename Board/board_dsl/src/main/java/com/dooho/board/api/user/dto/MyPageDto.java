package com.dooho.board.api.user.dto;

import com.dooho.board.api.board.dto.BoardDto;

import java.util.List;


public record MyPageDto(
        String userEmail,
        String userNickname,
        String userProfile,
        List<BoardDto> userBoard
){
    public static MyPageDto of(String userEmail, String userNickname, String userProfile, List<BoardDto> userBoard) {
        return new MyPageDto(userEmail, userNickname, userProfile, userBoard);
    }
}