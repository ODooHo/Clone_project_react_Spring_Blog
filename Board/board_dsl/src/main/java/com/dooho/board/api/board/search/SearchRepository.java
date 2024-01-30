package com.dooho.board.api.board.search;

import com.dooho.board.api.board.search.querydsl.CustomSearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SearchRepository extends JpaRepository<SearchEntity, String> , CustomSearchRepository {

    boolean existsByPopularTerm(String search);

}
