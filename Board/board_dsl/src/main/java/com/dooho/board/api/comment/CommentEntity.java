package com.dooho.board.api.comment;

import com.dooho.board.api.board.BoardEntity;
import com.dooho.board.api.comment.dto.CommentDto;
import com.dooho.board.api.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@Entity(name = "Comment")
@Table(name = "Comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate commentWriteDate;
    private String commentContent;

    @Setter
    @JsonIgnore
    @ManyToOne(optional = false)
    private BoardEntity board; // 게시글(ID)

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "userEmail")
    private UserEntity user; // 유저 정보 (ID)

    protected CommentEntity(){

    }

    private CommentEntity(Integer id, LocalDate commentWriteDate, String commentContent, BoardEntity board, UserEntity user) {
        this.id = id;
        this.commentWriteDate = commentWriteDate;
        this.commentContent = commentContent;
        this.board = board;
        this.user = user;
    }

    public static CommentEntity of(Integer id, LocalDate commentWriteDate, String commentContent, BoardEntity board, UserEntity user) {
        return new CommentEntity(id,commentWriteDate,commentContent,board,user);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        CommentEntity that = (CommentEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
