package com.dooho.board.api.board.search.querydsl;

import com.dooho.board.api.board.search.SearchEntity;

import java.util.List;

public interface CustomSearchRepository {
    List<SearchEntity> findTop10();
}
