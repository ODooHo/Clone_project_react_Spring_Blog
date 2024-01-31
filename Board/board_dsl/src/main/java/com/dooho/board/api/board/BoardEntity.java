package com.dooho.board.api.board;

import com.dooho.board.api.board.liky.LikyEntity;
import com.dooho.board.api.comment.CommentEntity;
import com.dooho.board.api.user.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@Entity(name="Board")
@Table(name="Board")
//GeneratedValue(strategy = 전략) 기본 키를 자동으로 생성해주는 어노테이션, IDENTITY = AUTOINCREMENT
//SEQUENCE = 오라클, Postgre 시퀀스를 지원, TABLE = 키 생성 전용 테이블 만들고 이름, 값을 만들어서 시퀀스를 흉내내는 것
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
    private String image;
    private String video;
    private String file;
    private LocalDate boardWriteDate;
    private int clickCount;
    private int likesCount;
    private int commentsCount;
    @ManyToOne
    @JoinColumn(name = "userEmail")
    private UserEntity user;

    @ToString.Exclude
    @OrderBy("commentWriteDate DESC")
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private final Set<CommentEntity> comments = new LinkedHashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private final Set<LikyEntity> likes = new LinkedHashSet<>();

    protected BoardEntity(){

    }

    private BoardEntity(Integer id, String title, String content, String image, String video, String file, LocalDate boardWriteDate, int clickCount,int likesCount,int commentsCount ,UserEntity user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.image = image;
        this.video = video;
        this.file = file;
        this.boardWriteDate = boardWriteDate;
        this.clickCount = clickCount;
        this.likesCount = likesCount;
        this.commentsCount = commentsCount;
        this.user = user;
    }

    public static BoardEntity of (Integer id, String title, String content, String image, String video, String file, LocalDate boardWriteDate, int clickCount, int likesCount, int commentsCount ,UserEntity user) {
        return new BoardEntity(id,title,content,image,video,file,boardWriteDate,clickCount,likesCount,commentsCount,user);
    }




    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        BoardEntity that = (BoardEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
