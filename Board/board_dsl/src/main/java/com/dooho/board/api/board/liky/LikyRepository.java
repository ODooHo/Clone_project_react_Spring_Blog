package com.dooho.board.api.board.liky;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikyRepository extends JpaRepository<LikyEntity,Integer> {

    Integer countByBoard_Id(Integer boardId);

    List<LikyEntity> findByBoard_Id(Integer boardId);

    void deleteByUser_UserEmail(String userEmail);


    void deleteByBoard_IdAndUser_UserEmail(Integer boardId, String userEmail);
}
