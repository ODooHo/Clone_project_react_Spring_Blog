package com.dooho.board.api.board

import java.time.LocalDate


data class PatchBoardDto (
    val boardTitle: String? = null,
    val boardContent: String? = null,
    val boardWriteDate: LocalDate? = null
)