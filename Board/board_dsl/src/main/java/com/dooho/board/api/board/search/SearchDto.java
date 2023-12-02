package com.dooho.board.api.board.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchDto {
    private String popularTerm;
    private Integer popularSearchCount;
}
