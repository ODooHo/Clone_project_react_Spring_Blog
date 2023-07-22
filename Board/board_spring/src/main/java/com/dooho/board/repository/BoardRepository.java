package com.dooho.board.repository;

import com.dooho.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

    public List<BoardEntity> findTop3ByBoardWriteDateAfterOrderByBoardLikeCountDesc(LocalDate boardWriteDate);

    public List<BoardEntity> findByOrderByBoardWriteDateDesc();


    public List<BoardEntity> findByBoardTitleContains(String boardTitle);

    public List<BoardEntity> findByBoardWriterEmail(String userEmail);




}
