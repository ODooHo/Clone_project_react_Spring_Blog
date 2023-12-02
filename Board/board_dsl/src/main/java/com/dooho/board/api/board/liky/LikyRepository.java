package com.dooho.board.api.board.liky;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface LikyRepository extends JpaRepository<LikyEntity,Integer> {

    Integer countByBoardNumber(Integer boardNumber);

    List<LikyEntity> findByBoardNumber(Integer boardNumber);

    void deleteByLikeUserNickname(String likeUserNickname);

    void deleteByLikeId(Integer likeId);

    void deleteByBoardNumberAndLikeUserNickname(Integer boardNumber, String likeUserNickname);
}
