package com.dooho.board.api.board.search;

import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.board.dto.BoardDto;
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
    public ResponseDto<List<BoardDto>> searchBoard(@RequestParam(required = false, name = "searchWord") String searchWord){
        return searchService.getSearchList(searchWord);
    }

    @GetMapping("/popularSearchList")
    public ResponseDto<List<SearchDto>> getPopularSearchList(){
        return searchService.getPopularSearchList();
    }
}
