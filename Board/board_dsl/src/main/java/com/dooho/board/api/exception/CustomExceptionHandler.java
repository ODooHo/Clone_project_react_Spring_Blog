package com.dooho.board.api.exception;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.dooho.board.api.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.dooho.board.api.exception.ErrorCode.DATABASE_ERROR;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BoardApplicationException.class)
    public ResponseEntity<?> errorHandler(BoardApplicationException e){
        log.error("Error occurs {}",e.toString());
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(ResponseDto.error(e.getErrorCode().name()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> databaseErrorHandler(IllegalArgumentException e) {
        log.error("Error occurs {}", e.toString());
        return ResponseEntity.status(DATABASE_ERROR.getStatus())
                .body(ResponseDto.error(DATABASE_ERROR.name()));
    }

    @ExceptionHandler({AmazonS3Exception.class})
    public ResponseEntity<?> amazonHandler(AmazonS3Exception e) {
        log.warn("AmazonS3 Exception!!");
        log.error("Error occurs {}", e.toString());
        return ResponseEntity
                .notFound()
                .build();
//                .contentType(MediaType.APPLICATION_JSON)
//                .body("{\"message\": \"failed\", \"data\": \"" + e.getMessage() + "\"}");
    }
}
