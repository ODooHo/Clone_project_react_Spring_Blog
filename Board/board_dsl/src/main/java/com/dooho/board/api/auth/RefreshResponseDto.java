package com.dooho.board.api.auth;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshResponseDto {
    private String token;
    private Integer tokenExprTime;
}
