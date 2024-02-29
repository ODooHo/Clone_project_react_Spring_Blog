package com.dooho.board.api.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid token"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not founded"),
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "Board not founded"),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND,"Comment not founded"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Invalid password"),
    INVALID_PASSWORD_CHECK(HttpStatus.UNAUTHORIZED, "Password check not matched"),
    DUPLICATED_USER_EMAIL(HttpStatus.CONFLICT, "Duplicated user name"),
    DUPLICATED_USER_NICKNAME(HttpStatus.CONFLICT, "Duplicated user nickname"),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "User has invalid permission"),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Database error occurs"),
    NOTIFICATION_CONNECT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Connect to notification occurs error"),
    ;

    private final HttpStatus status;
    private final String message;
}
