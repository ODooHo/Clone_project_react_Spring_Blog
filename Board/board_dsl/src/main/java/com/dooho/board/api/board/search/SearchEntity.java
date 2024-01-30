package com.dooho.board.api.board.search;

import com.dooho.board.api.board.search.dto.SearchDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@Entity(name = "Search")
@Table(name = "Search")
public class SearchEntity {
    @Id
    private String popularTerm;
    private Integer popularSearchCount;


    protected SearchEntity() {

    }

    private SearchEntity(String popularTerm, Integer popularSearchCount) {
        this.popularTerm = popularTerm;
        this.popularSearchCount = popularSearchCount;
    }

    public static SearchEntity of(String popularTerm, Integer popularSearchCount) {
        return new SearchEntity(popularTerm,popularSearchCount);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        SearchEntity that = (SearchEntity) o;
        return getPopularTerm() != null && Objects.equals(getPopularTerm(), that.getPopularTerm());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
