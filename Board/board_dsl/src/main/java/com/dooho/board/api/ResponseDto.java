package com.dooho.board.api;


import jakarta.annotation.Nullable;
import org.springframework.http.ResponseEntity;

public record ResponseDto<D>(
        String message,
        D data
){

    public static <D> ResponseEntity<ResponseDto<D>> setSuccess(String message, D data) {
        ResponseDto<D> responseDto = new ResponseDto<>(message,data);
        return ResponseEntity.ok(responseDto);
    }

}

