package com.dooho.board.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor(staticName = "set")
public class ResponseDto<D>{
    private boolean result;
    private String message;
    private D data;
    private HttpStatus statusCode;
    public static <D> ResponseDto<D> setSuccess(String message, D data){
        return ResponseDto.set(true,message,data,HttpStatus.OK);
    }

    public static <D> ResponseDto<D> setFailed(String message, HttpStatus statusCode){
        return ResponseDto.set(false,message,null,statusCode);
    }

}
