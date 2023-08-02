package com.dooho.board.dto.comment;

import com.dooho.board.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatchCommentResponseDto {
    private CommentEntity comment;
}
