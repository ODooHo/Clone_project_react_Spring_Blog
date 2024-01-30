package com.dooho.board.api.comment.querydsl;

import com.dooho.board.api.comment.CommentEntity;

import java.util.List;

public interface CustomCommentRepository {
    List<CommentEntity> getComment(Integer boardId);

}
