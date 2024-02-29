package com.dooho.board.api.auth;

import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.auth.dto.RefreshResponseDto;
import com.dooho.board.api.auth.dto.SignInDto;
import com.dooho.board.api.auth.dto.SignInResponseDto;
import com.dooho.board.api.auth.dto.SignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthService authService;


    @PostMapping("/signUp")
    public ResponseDto<Void> signUp(@RequestBody SignUpDto requestBody){
        authService.signUp(requestBody);
        return ResponseDto.setSuccess();
    }

    @PostMapping("/signIn")
    public ResponseDto<SignInResponseDto> signIn(@RequestBody SignInDto requestBody){
        SignInResponseDto response = authService.signIn(requestBody);
        return ResponseDto.setSuccess(response);
    }

    @PostMapping("/getAccess")
    public ResponseDto<RefreshResponseDto> getAccess(@RequestBody String refreshToken){
        RefreshResponseDto response = authService.getAccess(refreshToken);
        return ResponseDto.setSuccess(response);
    }
}
