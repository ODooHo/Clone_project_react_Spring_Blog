package com.dooho.board.api.board.search;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="Search")
@Table(name="Search")
public class SearchEntity {
    @Id
    private String popularTerm;
    private Integer popularSearchCount;

    public SearchEntity(SearchDto dto) {
        this.popularTerm = dto.getPopularTerm();
        this.popularSearchCount = dto.getPopularSearchCount();
    }

    public void setPopularSearchCount(Integer popularSearchCount) {
        this.popularSearchCount = popularSearchCount;
    }
}
