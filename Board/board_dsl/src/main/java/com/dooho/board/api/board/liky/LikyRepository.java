package com.dooho.board.api.board.liky;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LikyRepository extends JpaRepository<LikyEntity,Integer> {

    Integer countByboardId(Integer boardId);

    List<LikyEntity> findByboardId(Integer boardId);

    void deleteByLikeUserNickname(String likeUserNickname);

    void deleteByLikeId(Integer likeId);

    void deleteByboardIdAndLikeUserNickname(Integer boardId, String likeUserNickname);
}
