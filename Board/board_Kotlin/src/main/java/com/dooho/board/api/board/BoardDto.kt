package com.dooho.board.api.board

import java.time.LocalDate

data class BoardDto(
    var boardNumber: Int = 0,
    var boardTitle: String? = null,
    var boardContent: String? = null,
    var boardImage: String? = null,
    var boardVideo: String? = null,
    var boardFile: String? = null,
    var boardWriterEmail: String? = null,
    var boardWriterProfile: String? = null,
    var boardWriterNickname: String? = null,
    var boardWriteDate: LocalDate? = null,
    var boardClickCount: Int = 0,
    var boardLikeCount: Int = 0,
    var boardCommentCount: Int = 0
)