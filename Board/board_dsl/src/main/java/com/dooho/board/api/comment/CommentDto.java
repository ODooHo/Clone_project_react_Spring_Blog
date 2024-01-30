package com.dooho.board.api.comment;

import com.dooho.board.api.board.BoardEntity;
import com.dooho.board.api.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private BoardEntity board;
    private UserEntity user;
    private LocalDate commentWriteDate;
    private String commentContent;
}
