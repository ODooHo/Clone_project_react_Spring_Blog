package com.dooho.board.api.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface CommentRepository extends JpaRepository<CommentEntity, Integer> ,CustomCommentRepository{

    Integer countByboardId(Integer boardId);

    void deleteByCommentId(Integer commentId);

    List<CommentEntity> findByUser_UserEmail(String userEmail);
}
