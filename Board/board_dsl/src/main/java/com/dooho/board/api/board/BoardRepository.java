package com.dooho.board.api.board;

import com.dooho.board.api.board.querydsl.CustomBoardRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public interface BoardRepository extends JpaRepository<BoardEntity, Integer>, CustomBoardRepository {

    List<BoardEntity> findByTitleContains(String boardTitle);

    List<BoardEntity> findByUser_UserEmail(String userEmail);

    boolean existsByTitle(String boardTitle);

}