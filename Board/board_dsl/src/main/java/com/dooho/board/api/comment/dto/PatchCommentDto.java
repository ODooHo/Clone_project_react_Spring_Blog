package com.dooho.board.api.comment.dto;

import java.time.LocalDate;


public record PatchCommentDto (
    String commentContent,
    LocalDate commentWriteDate
){}
