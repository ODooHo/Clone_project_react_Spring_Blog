package com.dooho.board.api.board.search;

import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.board.BoardEntity;
import com.dooho.board.api.board.search.dto.SearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    @PostMapping("")
    public ResponseDto<List<BoardEntity>> searchBoard(@RequestBody SearchDto requestBody){
        return searchService.getSearchList(requestBody);
    }

    @GetMapping("/popularSearchList")
    public ResponseDto<List<SearchEntity>> getPopularSearchList(){
        return searchService.getPopularSearchList();
    }
}
