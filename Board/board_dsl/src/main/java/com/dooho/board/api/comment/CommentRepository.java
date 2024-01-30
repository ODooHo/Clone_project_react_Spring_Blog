package com.dooho.board.api.comment;

import com.dooho.board.api.comment.querydsl.CustomCommentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<CommentEntity, Integer> , CustomCommentRepository {

    Integer countByBoard_Id(Integer boardId);

    List<CommentEntity> findByUser_UserEmail(String userEmail);

    List<CommentEntity> findAllByBoard_Id (Integer boardId);
}
