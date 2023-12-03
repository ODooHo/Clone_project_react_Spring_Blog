package com.dooho.board.api.board

import com.dooho.board.api.board.BoardEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Repository
@Transactional
interface BoardRepository : JpaRepository<BoardEntity?, Int?> {
    fun findTop3ByBoardWriteDateAfterOrderByBoardLikeCountDesc(boardWriteDate: LocalDate?): List<BoardEntity?>?
    fun findByOrderByBoardWriteDateDescBoardNumberDesc(): List<BoardEntity?>?
    fun findByBoardTitleContains(boardTitle: String?): List<BoardEntity?>?
    fun findByBoardWriterEmail(userEmail: String?): List<BoardEntity?>?
    fun existsByBoardTitle(boardTitle: String?): Boolean
    fun findByBoardNumber(boardNumber: Int?): BoardEntity?
    fun deleteBoardEntityByBoardNumber(BoardNumber: Int?)
}