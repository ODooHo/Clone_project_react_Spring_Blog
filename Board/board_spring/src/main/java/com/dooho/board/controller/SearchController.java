package com.dooho.board.controller;

import com.dooho.board.dto.SearchDto;
import com.dooho.board.dto.ResponseDto;
import com.dooho.board.entity.BoardEntity;
import com.dooho.board.entity.SearchEntity;
import com.dooho.board.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }


    @PostMapping("")
    public ResponseDto<List<BoardEntity>> searchBoard(@RequestBody SearchDto requestBody){
        ResponseDto<List<BoardEntity>> result = searchService.getSearchList(requestBody);
        return result;
    }

    @GetMapping("/popularSearchList")
    public ResponseDto<List<SearchEntity>> getPopularSearchList(){
        ResponseDto<List<SearchEntity>> result = searchService.getPopularSearchList();
        return result;
    }
}
