package com.dooho.board.api.comment.dto;

import com.dooho.board.api.board.BoardEntity;
import com.dooho.board.api.comment.CommentEntity;
import com.dooho.board.api.user.UserEntity;
import com.dooho.board.api.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public record CommentDto (
        Integer id,
        Integer boardId,
        UserDto user,
        LocalDate commentWriteDate,
        String commentContent
){


    public static CommentDto of(Integer boardId, UserDto user, LocalDate commentWriteDate, String commentContent) {
        return new CommentDto(null,boardId,user,commentWriteDate,commentContent);
    }
    public static CommentDto of(Integer id, Integer boardId, UserDto user, LocalDate commentWriteDate, String commentContent) {
        return new CommentDto(id,boardId,user,commentWriteDate,commentContent);
    }

    public static CommentDto from(CommentEntity comment){
        return new CommentDto(
                comment.getId(),
                comment.getBoard().getId(),
                UserDto.from(comment.getUser()),
                comment.getCommentWriteDate(),
                comment.getCommentContent()
                );
    }
}