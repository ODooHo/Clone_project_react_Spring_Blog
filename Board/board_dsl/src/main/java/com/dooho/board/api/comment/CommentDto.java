package com.dooho.board.api.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
            private Integer commentId;
    private Integer boardNumber;
    private String userEmail;
    private String commentUserProfile;
    private String commentUserNickname;
    private LocalDate commentWriteDate;
    private String commentContent;
}
