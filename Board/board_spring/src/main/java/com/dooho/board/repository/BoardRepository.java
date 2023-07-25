package com.dooho.board.repository;

import com.dooho.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

    List<BoardEntity> findTop3ByBoardWriteDateAfterOrderByBoardLikeCountDesc(LocalDate boardWriteDate);

    List<BoardEntity> findByOrderByBoardWriteDateDesc();


    List<BoardEntity> findByBoardTitleContains(String boardTitle);

    List<BoardEntity> findByBoardWriterEmail(String userEmail);

    boolean existsByBoardTitle(String boardTitle);

    BoardEntity findByBoardNumber(Integer boardNumber);



}
