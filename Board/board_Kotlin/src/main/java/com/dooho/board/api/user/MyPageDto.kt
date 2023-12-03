package com.dooho.board.api.user

import com.dooho.board.api.board.BoardEntity


data class MyPageDto (
    val userEmail: String? = null,
    val userNickname: String? = null,
    val userProfile: String? = null,
    val userBoard: List<BoardEntity?>? = null
    )