package com.dooho.board.api.comment

import com.dooho.board.api.comment.CommentDto
import jakarta.persistence.*
import java.time.LocalDate

@Entity(name = "Comment")
@Table(name = "Comment")
data class CommentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var commentId: Int? = null,
    var boardNumber: Int? = null,
    var userEmail: String? = null,
    var commentUserProfile: String? = null,
    var commentUserNickname: String? = null,
    var commentWriteDate: LocalDate? = null,
    var commentContent: String? = null
) {
    constructor(dto: CommentDto?) : this(
        dto?.commentId,
        dto?.boardNumber,
        dto?.userEmail,
        dto?.commentUserProfile,
        dto?.commentUserNickname,
        dto?.commentWriteDate,
        dto?.commentContent
    )
}