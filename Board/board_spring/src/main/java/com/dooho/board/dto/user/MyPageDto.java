package com.dooho.board.dto.user;

import com.dooho.board.entity.BoardEntity;
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
