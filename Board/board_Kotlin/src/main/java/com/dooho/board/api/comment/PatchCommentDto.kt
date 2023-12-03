package com.dooho.board.api.comment


import java.time.LocalDate


data class PatchCommentDto (
    val commentContent: String? = null,
    val commentWriteDate: LocalDate? = null
)