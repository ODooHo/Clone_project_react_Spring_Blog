package com.dooho.board.api.comment;

import com.dooho.board.api.board.BoardEntity;
import com.dooho.board.api.comment.CommentDto;
import com.dooho.board.api.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Comment")
@Table(name = "Comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;
    private String commentUserProfile;
    private String commentUserNickname;
    private LocalDate commentWriteDate;
    private String commentContent;

    @Setter
    @ManyToOne(optional = false)
    private BoardEntity board; // 게시글(ID)

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "userEmail")
    private UserEntity user; // 유저 정보 (ID)

    public CommentEntity(CommentDto dto){
        this.board = dto.getBoard();
        this.user = dto.getUser();
        this.commentWriteDate = dto.getCommentWriteDate();
        this.commentContent = dto.getCommentContent();
    }
}
