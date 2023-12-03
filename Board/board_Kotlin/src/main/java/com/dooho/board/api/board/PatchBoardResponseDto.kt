package com.dooho.board.api.board

import com.dooho.board.api.board.BoardEntity


data class PatchBoardResponseDto (
    val board: BoardEntity? = null
)