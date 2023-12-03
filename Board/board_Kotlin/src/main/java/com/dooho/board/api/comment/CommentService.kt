package com.dooho.board.api.comment

import com.dooho.board.api.ResponseDto
import com.dooho.board.api.ResponseDto.Companion.setFailed
import com.dooho.board.api.ResponseDto.Companion.setSuccess
import com.dooho.board.api.board.BoardRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class CommentService @Autowired constructor(
    private val commentRepository: CommentRepository,
    private val boardRepository: BoardRepository
) {
    fun register(dto: CommentDto?): ResponseDto<CommentEntity?> {
        val commentEntity = CommentEntity(dto)
        try {
            commentRepository.save(commentEntity)
        } catch (e: Exception) {
            e.printStackTrace()
            return setFailed("DataBase Error!")
        }
        return setSuccess("Success", commentEntity)
    }

    fun getComment(boardNumber: Int?): ResponseDto<List<CommentEntity?>?> {
        var commentList: List<CommentEntity?>? = ArrayList()
        val boardEntity = boardRepository.findById(boardNumber).orElse(null)!!
        var commentCount: Int? = 0
        try {
            commentList = commentRepository.findByBoardNumberOrderByCommentWriteDate(boardNumber)
            commentCount = commentRepository.countByBoardNumber(boardNumber)
            if (commentCount != null) {
                boardEntity.boardCommentCount = commentCount
            }
            boardRepository.save(boardEntity)
        } catch (e: Exception) {
            e.printStackTrace()
            return setFailed("DataBase Error!")
        }
        return setSuccess("Success", commentList)
    }

    fun editComment(boardNumber: Int?, commentId: Int?, dto: PatchCommentDto?): ResponseDto<PatchCommentResponseDto?> {
        var comment: CommentEntity? = null
        val commentContent: String? = dto?.commentContent
        val commentWriteDate: LocalDate? = dto?.commentWriteDate
        try {
            comment = commentRepository.findById(commentId).orElse(null)
            comment!!.commentContent = commentContent
            comment.commentWriteDate = commentWriteDate
            commentRepository.save(comment)
        } catch (e: Exception) {
            e.printStackTrace()
            return setFailed("DataBase Error!")
        }
        val patchCommentResponseDto = PatchCommentResponseDto(comment)
        return setSuccess("Success!", patchCommentResponseDto)
    }

    fun deleteComment(boardNumber: Int?, commentId: Int?): ResponseDto<*> {
        val boardEntity = boardRepository.findById(boardNumber).orElse(null)!!
        try {
            commentRepository.deleteByCommentId(commentId)
            val temp = boardEntity.boardCommentCount
            boardEntity.boardCommentCount = temp?.minus(1)
            boardRepository.save(boardEntity)
        } catch (e: Exception) {
            e.printStackTrace()
            return setFailed<Any>("DataBase Error!")
        }
        return setSuccess<Any?>("Success", null)
    }
}