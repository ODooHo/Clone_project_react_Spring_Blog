package com.dooho.board.api.board.search.dto;

import com.dooho.board.api.board.search.SearchEntity;


public record PopularSearchDto(
    String popularTerm,
    Integer popularSearchCount
){
    public static PopularSearchDto of(String popularTerm, Integer popularSearchCount){
        return new PopularSearchDto(popularTerm,popularSearchCount);
    }


    public static PopularSearchDto from(SearchEntity search){
        return new PopularSearchDto(search.getPopularTerm(),search.getPopularSearchCount());
    }

    public SearchEntity toEntity(){
        return SearchEntity.of(
                popularTerm,
                popularSearchCount
        );
    }

}

