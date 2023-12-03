package com.dooho.board.api.board.search



data class SearchDto (
    val popularTerm: String? = null,
    val popularSearchCount: Int? = null
)