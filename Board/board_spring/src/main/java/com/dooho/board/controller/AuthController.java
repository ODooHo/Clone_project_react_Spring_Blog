package com.dooho.board.controller;

import com.dooho.board.dto.ResponseDto;
import com.dooho.board.dto.auth.RefreshResponseDto;
import com.dooho.board.dto.auth.SignInDto;
import com.dooho.board.dto.auth.SignInResponseDto;
import com.dooho.board.dto.auth.SignUpDto;
import com.dooho.board.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/signUp")
    public ResponseDto<?> signUp(@RequestBody SignUpDto requestBody){
        ResponseDto<?> result = authService.signUp(requestBody);
        return result;
    }

    @PostMapping("/signIn")
    public ResponseDto<SignInResponseDto> signIn(@RequestBody SignInDto requestBody){
        ResponseDto<SignInResponseDto> result = authService.signIn(requestBody);
        return result;
    }

    @PostMapping("/getAccess")
    public ResponseDto<RefreshResponseDto> getAccess(@RequestBody String refreshToken){
        ResponseDto<RefreshResponseDto> result = authService.getAccess(refreshToken);
        return result;
    }
}
