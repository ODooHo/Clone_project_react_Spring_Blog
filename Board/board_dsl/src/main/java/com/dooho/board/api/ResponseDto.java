package com.dooho.board.api;


import jakarta.annotation.Nullable;
import org.springframework.http.ResponseEntity;

public record ResponseDto<T>(
        String message,
        T data
){

    public static <T> ResponseDto<T> setSuccess() {
        return new ResponseDto<T>("Success",null);
    }

    public static <T> ResponseDto<T> setSuccess(T data) {
        return new ResponseDto<>("Success",data);
    }

    public static ResponseDto<Void> error(String message) {
        return new ResponseDto<Void>(message, null);
    }


    public String toStream() {
        if (data == null) {
            return "{" +
                    "\"message\":" + "\"" + message + "\"," +
                    "\"data\":" + null +
                    "}";
        }
        return "{" +
                "\"message\":" + "\"" + message + "\"," +
                "\"data\":" + "\"" + data + "\"," +
                "}";
    }

}

