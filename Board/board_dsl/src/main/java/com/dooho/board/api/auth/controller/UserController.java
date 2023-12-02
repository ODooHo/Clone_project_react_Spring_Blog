package com.dooho.board.api.auth.controller;

import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.user.MyPageDto;
import com.dooho.board.api.user.PatchUserDto;
import com.dooho.board.api.user.PatchUserResponseDto;
import com.dooho.board.api.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin(origins = "*")
    @PatchMapping("/edit")
    public ResponseDto<PatchUserResponseDto> patchUser(@RequestBody PatchUserDto requestBody, @AuthenticationPrincipal String userEmail){
        ResponseDto<PatchUserResponseDto> result = userService.patchUser(requestBody,userEmail);

        return result;
    }

    @GetMapping("/myPage")
    public ResponseDto<MyPageDto> myPage(@AuthenticationPrincipal String userEmail){
        ResponseDto<MyPageDto> result = userService.myPage(userEmail);

        return result;

    }
}
