package com.dooho.board.api.board;

import com.dooho.board.api.board.BoardDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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
    private Integer boardNumber;
    private String boardTitle;
    private String boardContent;
    private String boardImage;
    private String boardVideo;
    private String boardFile;
    private String boardWriterEmail;
    private String boardWriterProfile;
    private String boardWriterNickname;
    private LocalDate boardWriteDate;
    private int boardClickCount;
    private int boardLikeCount;
    private int boardCommentCount;

    public BoardEntity(BoardDto dto) {
        this.boardNumber = dto.getBoardNumber();
        this.boardTitle = dto.getBoardTitle();
        this.boardContent = dto.getBoardContent();
        this.boardImage = dto.getBoardImage();
        this.boardVideo = dto.getBoardVideo();
        this.boardFile = dto.getBoardFile();
        this.boardWriterEmail = dto.getBoardWriterEmail();
        this.boardWriterProfile = dto.getBoardWriterProfile();
        this.boardWriterNickname = dto.getBoardWriterNickname();
        this.boardWriteDate = dto.getBoardWriteDate();
        this.boardClickCount = dto.getBoardClickCount();
        this.boardLikeCount = dto.getBoardLikeCount();
        this.boardCommentCount = dto.getBoardCommentCount();
    }
    public void setBoardLikeCount(Integer boardLikeCount) {
        this.boardLikeCount = boardLikeCount;
    }

    public void setBoardCommentCount(Integer boardCommentCount){
        this.boardCommentCount = boardCommentCount;
    }

    public void setClickCount(Integer boardClickCount){
        this.boardClickCount = boardClickCount;
    }
}
