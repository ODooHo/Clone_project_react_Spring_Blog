package com.dooho.board.api.comment

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface CommentRepository : JpaRepository<CommentEntity?, Int?> {
    fun findByBoardNumberOrderByCommentWriteDate(boardNumber: Int?): List<CommentEntity?>?
    fun countByBoardNumber(boardNumber: Int?): Int?
    fun deleteByCommentId(commentId: Int?)
    fun findByUserEmail(userEmail: String?): List<CommentEntity?>?
}