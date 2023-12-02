package com.dooho.board.api.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Repository
@Transactional
public interface BoardRepository extends JpaRepository<BoardEntity, Integer>, CustomBoardRepository {

    List<BoardEntity> findByBoardTitleContains(String boardTitle);

    List<BoardEntity> findByBoardWriterEmail(String userEmail);

    boolean existsByBoardTitle(String boardTitle);

    BoardEntity findByBoardNumber(Integer boardNumber);

    void deleteBoardEntityByBoardNumber(Integer BoardNumber);


}