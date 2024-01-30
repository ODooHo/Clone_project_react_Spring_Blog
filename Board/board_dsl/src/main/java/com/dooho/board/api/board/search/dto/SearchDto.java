package com.dooho.board.api.board.search.dto;

import com.dooho.board.api.board.search.SearchEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public record SearchDto(
    String popularTerm,
    Integer popularSearchCount
){
    public static SearchDto of(String popularTerm, Integer popularSearchCount){
        return new SearchDto(popularTerm,popularSearchCount);
    }


    public static SearchDto from(SearchEntity search){
        return new SearchDto(search.getPopularTerm(),search.getPopularSearchCount());
    }

}

