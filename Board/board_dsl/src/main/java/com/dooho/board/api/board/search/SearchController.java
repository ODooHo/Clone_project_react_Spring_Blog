package com.dooho.board.api.board.search;

import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.board.dto.BoardDto;
import com.dooho.board.api.board.search.dto.PopularSearchDto;
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
    public ResponseDto<List<BoardDto>> searchBoard(@RequestBody SearchDto requestBody){
        return searchService.getSearchList(requestBody);
    }

    @GetMapping("/popularSearchList")
    public ResponseDto<List<PopularSearchDto>> getPopularSearchList(){
        return searchService.getPopularSearchList();
    }
}
