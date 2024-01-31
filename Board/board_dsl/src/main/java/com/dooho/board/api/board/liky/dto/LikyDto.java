package com.dooho.board.api.board.liky.dto;

import com.dooho.board.api.board.dto.BoardDto;
import com.dooho.board.api.board.liky.LikyEntity;
import com.dooho.board.api.user.dto.UserDto;


public record LikyDto(
        Integer id,
        Integer boardId,
        String userEmail
) {
    public static LikyDto of(Integer boardId, String userEmail) {
        return new LikyDto(null, boardId, userEmail);
    }

    public static LikyDto of(Integer id, Integer boardId , String userEmail) {
        return new LikyDto(id, boardId, userEmail);
    }

    public static LikyDto from(LikyEntity liky) {
        return new LikyDto(
                liky.getId(),
                liky.getBoard().getId(),
                liky.getUser().getUserEmail()
        );
    }


}
