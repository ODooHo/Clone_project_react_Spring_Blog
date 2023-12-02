package com.dooho.board.api.board.search;

import java.util.List;

public interface CustomSearchRepository {
    List<SearchEntity> findTop10();
}
