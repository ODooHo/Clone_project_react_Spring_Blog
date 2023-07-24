package com.dooho.board.repository;

import com.dooho.board.entity.LikyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface LikyRepository extends JpaRepository<LikyEntity,Integer> {
}
