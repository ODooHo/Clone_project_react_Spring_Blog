package com.dooho.board.api.board.search

import com.dooho.board.api.ResponseDto
import com.dooho.board.api.board.BoardEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/search")
class SearchController @Autowired constructor(private val searchService: SearchService) {
    @PostMapping("")
    fun searchBoard(@RequestBody requestBody: SearchDto?): ResponseDto<List<BoardEntity?>?> {
        return searchService.getSearchList(requestBody)
    }

    @get:GetMapping("/popularSearchList")
    val popularSearchList: ResponseDto<List<SearchEntity?>?>
        get() = searchService.popularSearchList
}