package com.dooho.board.api.util;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.dooho.board.api.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
    public ResponseDto<?> runtimeHandler(RuntimeException e) {
        log.warn("Runtime Exception!!");
        log.error("Error log: {}", e.getMessage());

        return ResponseDto.setFailed(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseDto<?> credentialsHandler(BadCredentialsException e) {
        log.warn("BadCredentials Exception!!");
        log.error("Error log: {}", e.getMessage());

        return ResponseDto.setFailed(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseDto<?> argumentHandler(IllegalArgumentException e) {
        log.warn("Illegal Argument Exception!!");
        log.error("Error log: {}", e.getMessage());

        return ResponseDto.setFailed(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IOException.class})
    public ResponseDto<?> ioHandler(IOException e){
        log.warn("IO Exception!!");
        log.error("Error log: {}",e.getMessage());

        return ResponseDto.setFailed(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler({AmazonS3Exception.class})
    public ResponseDto<?> amazonHandler(AmazonS3Exception e){
        log.warn("AmazonS3 Exception!!");
        log.error("Error log: {}", e.getMessage());

        return ResponseDto.setFailed(e.getMessage(),HttpStatus.NOT_FOUND);
    }
}
