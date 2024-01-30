package com.dooho.board.api.auth;

import com.dooho.board.api.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthService authService;


    @PostMapping("/signUp")
    public ResponseDto<?> signUp(@RequestBody SignUpDto requestBody){
        return authService.signUp(requestBody);
    }

    @PostMapping("/signIn")
    public ResponseDto<SignInResponseDto> signIn(@RequestBody SignInDto requestBody){
        return authService.signIn(requestBody);
    }

    @PostMapping("/getAccess")
    public ResponseDto<RefreshResponseDto> getAccess(@RequestBody String refreshToken){
        return authService.getAccess(refreshToken);
    }
}
