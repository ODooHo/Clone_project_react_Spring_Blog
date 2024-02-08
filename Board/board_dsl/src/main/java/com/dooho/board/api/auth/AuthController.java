package com.dooho.board.api.auth;

import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.auth.dto.RefreshResponseDto;
import com.dooho.board.api.auth.dto.SignInDto;
import com.dooho.board.api.auth.dto.SignInResponseDto;
import com.dooho.board.api.auth.dto.SignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseDto<String>> signUp(@RequestBody SignUpDto requestBody){
        return authService.signUp(requestBody);
    }

    @PostMapping("/signIn")
    public ResponseEntity<ResponseDto<SignInResponseDto>> signIn(@RequestBody SignInDto requestBody){
        return authService.signIn(requestBody);
    }

    @PostMapping("/getAccess")
    public ResponseEntity<ResponseDto<RefreshResponseDto>> getAccess(@RequestBody String refreshToken){
        return authService.getAccess(refreshToken);
    }
}
