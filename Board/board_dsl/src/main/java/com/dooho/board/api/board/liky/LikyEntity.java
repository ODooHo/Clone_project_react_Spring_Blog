package com.dooho.board.api.board.liky;

import com.dooho.board.api.board.BoardEntity;
import com.dooho.board.api.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@Entity(name = "Liky")
@Table(name = "Liky")
public class LikyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "boardId")
    private BoardEntity board;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userEmail")
    private UserEntity user;

    protected LikyEntity(){

    }
    private LikyEntity(Integer id, BoardEntity board, UserEntity user) {
        this.id = id;
        this.board = board;
        this.user = user;
    }

    public static LikyEntity of(Integer id, BoardEntity board, UserEntity user){
        return new LikyEntity(id,board,user);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        LikyEntity that = (LikyEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
