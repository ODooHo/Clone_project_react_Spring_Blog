package com.dooho.board.controller;

import com.dooho.board.dto.*;
import com.dooho.board.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @PatchMapping("/edit")
    public ResponseDto<PatchUserResponseDto> patchUser(@RequestBody PatchUserDto requestBody, @AuthenticationPrincipal String userEmail){
        return null;
    }

    @GetMapping("/myPage")
    public ResponseDto<MyPageDto> myPage(@AuthenticationPrincipal String userEmail){
        ResponseDto<MyPageDto> result = userService.myPage(userEmail);

        return result;

    }
}
