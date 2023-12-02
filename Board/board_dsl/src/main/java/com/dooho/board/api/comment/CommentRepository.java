package com.dooho.board.api.comment;

import com.dooho.board.api.comment.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> ,CustomCommentRepository{

    Integer countByBoardNumber(Integer boardNumber);

    void deleteByCommentId(Integer commentId);

    List<CommentEntity> findByUserEmail(String userEmail);
}
