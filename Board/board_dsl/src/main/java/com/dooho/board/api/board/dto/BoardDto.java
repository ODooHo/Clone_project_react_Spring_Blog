package com.dooho.board.api.board.dto;

import com.dooho.board.api.board.BoardEntity;
import com.dooho.board.api.board.liky.dto.LikyDto;
import com.dooho.board.api.user.UserEntity;
import com.dooho.board.api.user.dto.UserDto;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;


public record BoardDto(
        Integer id,
        String title,
        String content,
        String image,
        String video,
        String file,
        LocalDate boardWriteDate,
        int clickCount,
        UserDto user,
        Set<LikyDto> likes

) {

    public static BoardDto of( String title, String content, String image, String video, String file, LocalDate boardWriteDate, int clickCount, UserDto user, Set<LikyDto> likes) {
        return new BoardDto(null, title, content, image, video, file, boardWriteDate, clickCount, user, likes);
    }

    public static BoardDto of(int id, String title, String content, String image, String video, String file, LocalDate boardWriteDate, int clickCount, UserDto user, Set<LikyDto> likes) {
        return new BoardDto(id, title, content, image, video, file, boardWriteDate, clickCount, user, likes);
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
                UserDto.from(board.getUser()),
                board.getLikes().stream()
                        .map(LikyDto::from)
                        .collect(Collectors.toUnmodifiableSet())
        );
    }

    public BoardEntity toEntity() {
        return BoardEntity.of(id,title,content,image,video,file,boardWriteDate,clickCount, user.toEntity());
    }
}
