package com.dooho.board.api.util;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> runtimeHandler(RuntimeException e) {
        log.warn("Runtime Exception!!");
        log.error("Error log: {}", e.getMessage());

        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"message\": \"failed\", \"data\": \"" + e.getMessage() + "\"}");
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<String> credentialsHandler(BadCredentialsException e) {
        log.warn("BadCredentials Exception!!");
        log.error("Error log: {}", e.getMessage());

        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"message\": \"failed\", \"data\": \"" + e.getMessage() + "\"}");
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<String> argumentHandler(IllegalArgumentException e) {
        log.warn("Illegal Argument Exception!!");
        log.error("Error log: {}", e.getMessage());

        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"message\": \"failed\", \"data\": \"" + e.getMessage() + "\"}");
    }

    @ExceptionHandler({IOException.class})
    public ResponseEntity<String> ioHandler(IOException e) {
        log.warn("IO Exception!!");
        log.error("Error log: {}", e.getMessage());

        return ResponseEntity
                .internalServerError()
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"message\": \"failed\", \"data\": \"" + e.getMessage() + "\"}");
    }

    @ExceptionHandler({AmazonS3Exception.class})
    public ResponseEntity<Object> amazonHandler(AmazonS3Exception e) {
        log.warn("AmazonS3 Exception!!");
        log.error("Error log: {}", e.getMessage());

        return ResponseEntity
                .notFound()
                .build();
//                .contentType(MediaType.APPLICATION_JSON)
//                .body("{\"message\": \"failed\", \"data\": \"" + e.getMessage() + "\"}");
    }
}
