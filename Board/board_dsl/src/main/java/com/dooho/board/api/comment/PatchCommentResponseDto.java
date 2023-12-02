package com.dooho.board.api.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatchCommentResponseDto {
    private CommentEntity comment;
}
