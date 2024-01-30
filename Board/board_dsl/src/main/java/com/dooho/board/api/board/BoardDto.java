package com.dooho.board.api.board;

import com.dooho.board.api.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
    private int id;
    private String title;
    private String content;
    private String image;
    private String video;
    private String file;
    private LocalDate boardWriteDate;
    private int clickCount;
    private int likeCount;
    private int commentCount;
    private UserEntity user;
}
