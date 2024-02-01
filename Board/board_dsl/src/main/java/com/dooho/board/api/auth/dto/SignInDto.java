package com.dooho.board.api.auth.dto;

import jakarta.validation.constraints.NotBlank;


public record SignInDto (
    @NotBlank
    String userEmail,
    @NotBlank
    String userPassword
){}

