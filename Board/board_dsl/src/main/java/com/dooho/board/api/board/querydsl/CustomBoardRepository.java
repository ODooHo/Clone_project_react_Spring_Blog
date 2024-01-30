package com.dooho.board.api.board.querydsl;

import com.dooho.board.api.board.BoardEntity;

import java.time.LocalDate;
import java.util.List;
public interface CustomBoardRepository {
    List<BoardEntity> findTop3(LocalDate boardWriteDate);
    List<BoardEntity> findList();
}