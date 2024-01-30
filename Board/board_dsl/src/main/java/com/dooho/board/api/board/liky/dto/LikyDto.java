package com.dooho.board.api.board.liky.dto;

import com.dooho.board.api.board.dto.BoardDto;
import com.dooho.board.api.board.liky.LikyEntity;
import com.dooho.board.api.user.dto.UserDto;


public record LikyDto(
        Integer id,
        BoardDto board,
        UserDto user
) {
    public static LikyDto of(BoardDto board, UserDto user) {
        return new LikyDto(null, board, user);
    }

    public static LikyDto of(Integer id, BoardDto board, UserDto user) {
        return new LikyDto(id, board, user);
    }

    public static LikyDto from(LikyEntity liky) {
        return new LikyDto(
                liky.getId(),
                BoardDto.from(liky.getBoard()),
                UserDto.from(liky.getUser())
        );
    }


}
