package com.dooho.board.api.board.search;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface SearchRepository extends JpaRepository<SearchEntity, String> ,CustomSearchRepository{

    boolean existsByPopularTerm(String search);

}
