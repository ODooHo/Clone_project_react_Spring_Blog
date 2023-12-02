package com.dooho.board.api.auth;

import com.dooho.board.api.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponseDto {
    private String token;
    private Integer tokenExprTime;
    private String refreshToken;
    private Integer refreshExprTime;
    private UserEntity user;
}
