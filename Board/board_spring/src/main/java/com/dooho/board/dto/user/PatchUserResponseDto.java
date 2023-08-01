package com.dooho.board.dto.user;

import com.dooho.board.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatchUserResponseDto {
    private UserEntity user;
}
