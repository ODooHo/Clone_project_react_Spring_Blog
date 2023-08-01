package com.dooho.board.dto.auth;

import com.dooho.board.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponseDto {
    private String token;
    private Integer exprTime;
    private UserEntity user;
}
