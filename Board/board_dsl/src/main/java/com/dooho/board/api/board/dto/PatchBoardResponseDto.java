package com.dooho.board.api.board.dto;

import com.dooho.board.api.board.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatchBoardResponseDto {
    private BoardEntity board;
}
