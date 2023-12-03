package com.dooho.board.api.comment

import java.time.LocalDate

data class CommentDto(

    val commentId: Int? = null,
    val boardNumber: Int? = null,
    val userEmail: String? = null,
    val commentUserProfile: String? = null,
    val commentUserNickname: String? = null,
    val commentWriteDate: LocalDate? = null,
    val commentContent: String? = null,

)