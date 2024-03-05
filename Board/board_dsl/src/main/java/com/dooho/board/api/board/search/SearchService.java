package com.dooho.board.api.board.search;

import com.dooho.board.api.board.BoardDocument;
import com.dooho.board.api.board.BoardRepository;
import com.dooho.board.api.board.BoardSearchRepository;
import com.dooho.board.api.board.dto.BoardDto;
import com.dooho.board.api.board.search.dto.PopularSearchDto;
import com.dooho.board.api.board.search.dto.SearchDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Slf4j
@Service
public class SearchService {
    private final BoardSearchRepository boardSearchRepository;

    private final BoardRepository boardRepository;

    private final SearchRepository searchRepository;

    public SearchService(BoardRepository boardRepository, SearchRepository searchRepository,
                         BoardSearchRepository boardSearchRepository) {
        this.boardRepository = boardRepository;
        this.searchRepository = searchRepository;
        this.boardSearchRepository = boardSearchRepository;
    }

    public List<BoardDto> getSearchList(SearchDto dto){
        String searchWord = dto.searchWord();
        SearchEntity searchEntity = searchRepository.findById(searchWord).orElse(null);
        if(searchEntity != null){
            Integer count = searchEntity.getPopularSearchCount();
            searchEntity.setPopularSearchCount(count+1);
            searchRepository.save(searchEntity);
        }else{
            PopularSearchDto searchDto = PopularSearchDto.of(searchWord, 1);
            searchRepository.save(searchDto.toEntity());
        }
        List<BoardDocument> byTitleContains = boardSearchRepository.findByTitleContains(searchWord);
        System.out.println(byTitleContains);

        return null;

//        return boardSearchRepository.findByTitleContains(searchWord)
//                .stream()
//                .map(BoardDto::from)
//                .toList();

    }


    @Transactional(readOnly = true)
    public List<PopularSearchDto> getPopularSearchList(){
        return searchRepository.findTop10()
                .stream()
                .map(PopularSearchDto::from)
                .toList();
    }

}
