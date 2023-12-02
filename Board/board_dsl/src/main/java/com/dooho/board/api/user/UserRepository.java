package com.dooho.board.api.user;

import com.dooho.board.api.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserEntity, String> {
    boolean existsByUserNickname(String userNickname);

}
