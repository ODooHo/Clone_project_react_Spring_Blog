package com.dooho.board.repository;

import com.dooho.board.entity.PopularSearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PopularSearchRepository extends JpaRepository<PopularSearchEntity, String> {
    List<PopularSearchEntity> findTop10ByOrderByPopularSearchCountDesc();

}
