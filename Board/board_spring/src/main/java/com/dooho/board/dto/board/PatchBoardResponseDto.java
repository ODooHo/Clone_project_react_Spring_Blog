package com.dooho.board.dto.board;

import com.dooho.board.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatchBoardResponseDto {
    private BoardEntity board;
}
