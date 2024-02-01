package com.dooho.board.api.user.dto;

import com.dooho.board.api.board.BoardEntity;
import com.dooho.board.api.board.dto.BoardDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyPageDto {
    private String userEmail;
    private String userNickname;
    private String userProfile;
    private List<BoardDto> userBoard;

}
