package com.dooho.board.api.board.search;

import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.board.BoardRepository;
import com.dooho.board.api.board.dto.BoardDto;
import com.dooho.board.api.board.search.dto.SearchDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Slf4j
@Service
public class SearchService {

    private final BoardRepository boardRepository;

    private final SearchRepository searchRepository;

    public SearchService(BoardRepository boardRepository, SearchRepository searchRepository) {
        this.boardRepository = boardRepository;
        this.searchRepository = searchRepository;
    }

    public ResponseDto<List<BoardDto>> getSearchList(String searchWord){
        SearchEntity searchEntity = searchRepository.findById(searchWord).orElse(null);

        if(searchEntity != null){
            Integer count = searchEntity.getPopularSearchCount();
            searchEntity.setPopularSearchCount(count+1);
            searchRepository.save(searchEntity);
        }else{
            SearchDto searchDto = SearchDto.of(searchWord, 1);
            searchRepository.save(searchDto.toEntity());
        }
        List<BoardDto> boardList = boardRepository.findByTitleContains(searchWord)
                .stream()
                .map(BoardDto::from)
                .toList();
        return ResponseDto.setSuccess("Success",boardList);
    }


    @Transactional(readOnly = true)
    public ResponseDto<List<SearchDto>> getPopularSearchList(){
        List<SearchDto> searchList = searchRepository.findTop10()
                .stream()
                .map(SearchDto::from)
                .toList();
        return ResponseDto.setSuccess("Success",searchList);
    }

}
