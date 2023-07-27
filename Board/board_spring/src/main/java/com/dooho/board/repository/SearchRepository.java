package com.dooho.board.repository;

import com.dooho.board.entity.SearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface SearchRepository extends JpaRepository<SearchEntity, String> {
    List<SearchEntity> findTop10ByOrderByPopularSearchCountDesc();

    boolean existsByPopularTerm(String search);

}
