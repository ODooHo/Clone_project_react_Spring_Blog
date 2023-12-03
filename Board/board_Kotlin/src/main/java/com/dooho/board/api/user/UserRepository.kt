package com.dooho.board.api.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface UserRepository : JpaRepository<UserEntity?, String?> {
    fun existsByUserEmailAndUserPassword(userEmail: String?, userPassword: String?): Boolean
    fun findByUserEmail(userEmail: String?): UserEntity?
    fun existsByUserNickname(userNickname: String?): Boolean
}