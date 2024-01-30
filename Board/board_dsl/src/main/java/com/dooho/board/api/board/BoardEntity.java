package com.dooho.board.api.board;

import com.dooho.board.api.comment.CommentEntity;
import com.dooho.board.api.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.mapping.Join;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private int likeCount;
    private int commentCount;

    @ManyToOne
    @JoinColumn(name = "userEmail")
    private UserEntity user;

    @ToString.Exclude
    @OrderBy("commentWriteDate DESC")
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private final Set<CommentEntity> comments= new LinkedHashSet<>();


    public BoardEntity(BoardDto dto) {
        this.id = dto.getId();
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.image = dto.getImage();
        this.video = dto.getVideo();
        this.file = dto.getFile();
        this.boardWriteDate = dto.getBoardWriteDate();
        this.clickCount = dto.getClickCount();
        this.likeCount = dto.getLikeCount();
        this.commentCount = dto.getCommentCount();
    }
    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public void setCommentCount(Integer commentCount){
        this.commentCount = commentCount;
    }

}
