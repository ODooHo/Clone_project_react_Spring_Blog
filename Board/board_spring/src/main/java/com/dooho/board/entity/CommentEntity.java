package com.dooho.board.entity;

import com.dooho.board.dto.comment.CommentDto;
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
    private Integer boardNumber;
    private String userEmail;
    private String commentUserProfile;
    private String commentUserNickname;
    private LocalDate commentWriteDate;
    private String commentContent;

    public CommentEntity(CommentDto dto){
        this.commentId = dto.getCommentId();
        this.boardNumber = dto.getBoardNumber();
        this.userEmail = dto.getUserEmail();
        this.commentUserProfile = dto.getCommentUserProfile();
        this.commentUserNickname = dto.getCommentUserNickname();
        this.commentWriteDate = dto.getCommentWriteDate();
        this.commentContent = dto.getCommentContent();

    }
}
