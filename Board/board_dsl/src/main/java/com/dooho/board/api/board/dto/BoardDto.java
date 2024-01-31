package com.dooho.board.api.board.dto;

import com.dooho.board.api.board.BoardEntity;
import com.dooho.board.api.user.dto.UserDto;

import java.time.LocalDate;


public record BoardDto(
        Integer id,
        String title,
        String content,
        String image,
        String video,
        String file,
        LocalDate boardWriteDate,
        int clickCount,
        int likesCount,
        int commentsCount,
        UserDto user

) {

    public static BoardDto of( String title, String content, String image, String video, String file, LocalDate boardWriteDate, int clickCount,int likesCount,int commentsCount,UserDto user) {
        return new BoardDto(null, title, content, image, video, file, boardWriteDate, clickCount,likesCount,commentsCount, user);
    }

    public static BoardDto of(int id, String title, String content, String image, String video, String file, LocalDate boardWriteDate, int clickCount,int likesCount,int commentsCount, UserDto user) {
        return new BoardDto(id, title, content, image, video, file, boardWriteDate, clickCount, likesCount,commentsCount,user);
    }

    public static BoardDto from(BoardEntity board) {
        return new BoardDto(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getImage(),
                board.getVideo(),
                board.getFile(),
                board.getBoardWriteDate(),
                board.getClickCount(),
                board.getLikes().size(),
                board.getComments().size(),
                UserDto.from(board.getUser())
        );
    }

    public BoardEntity toEntity() {
        return BoardEntity.of(id,title,content,image,video,file,boardWriteDate,clickCount,likesCount,commentsCount,user.toEntity());
    }
}
