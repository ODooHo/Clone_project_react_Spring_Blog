package com.dooho.board.api.user.dto;

import com.dooho.board.api.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatchUserResponseDto {
    private UserEntity user;
}
