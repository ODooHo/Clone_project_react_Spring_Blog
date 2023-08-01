package com.dooho.board.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatchBoardDto {
    private String boardTitle;
    private String boardContent;
    private LocalDate boardWriteDate;
}
