package com.dooho.board.repository;

import com.dooho.board.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserEntity, String> {
    boolean existsByUserEmailAndUserPassword(String userEmail, String userPassword);

    UserEntity findByUserEmail(String userEmail);

}
