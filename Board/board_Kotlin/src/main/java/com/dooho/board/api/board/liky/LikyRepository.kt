package com.dooho.board.api.board.liky

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface LikyRepository : JpaRepository<LikyEntity?, Int?> {
    fun countByBoardNumber(boardNumber: Int?): Int?
    fun findByBoardNumber(boardNumber: Int?): List<LikyEntity?>?
    fun deleteByLikeUserNickname(likeUserNickname: String?)
    fun deleteByLikeId(likeId: Int?)
    fun deleteByBoardNumberAndLikeUserNickname(boardNumber: Int?, likeUserNickname: String?)
}