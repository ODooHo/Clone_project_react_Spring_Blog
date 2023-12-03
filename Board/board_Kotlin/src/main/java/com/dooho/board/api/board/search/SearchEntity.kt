package com.dooho.board.api.board.search

import com.dooho.board.api.board.search.SearchDto
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity(name = "Search")
@Table(name = "Search")
data class SearchEntity(
    @Id
    var popularTerm: String? = null,
    var popularSearchCount: Int? = null
) {
    constructor(dto: SearchDto?) : this(
        popularTerm = dto?.popularTerm,
        popularSearchCount = dto?.popularSearchCount
    )
}