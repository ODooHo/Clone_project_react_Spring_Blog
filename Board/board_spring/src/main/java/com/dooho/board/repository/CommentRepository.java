package com.dooho.board.repository;

import com.dooho.board.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    List<CommentEntity> findByBoardNumberOrderByCommentWriteDate(Integer boardNumber);

    Integer countByBoardNumber(Integer boardNumber);

    void deleteByCommentId(Integer commentId);

    List<CommentEntity> findByUserEmail(String userEmail);
}
