package com.dooho.board.api.user;

import com.dooho.board.api.board.BoardEntity;
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
    private List<BoardEntity> userBoard;

}
