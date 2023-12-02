package com.dooho.board.api.comment;

import java.util.List;

public interface CustomCommentRepository {
    List<CommentEntity> getComment(Integer boardNumber);
}
