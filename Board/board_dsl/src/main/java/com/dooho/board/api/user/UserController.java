package com.dooho.board.api.user;

import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.user.dto.MyPageDto;
import com.dooho.board.api.user.dto.PatchUserDto;
import com.dooho.board.api.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @CrossOrigin(origins = "*")
    @PatchMapping("/edit")
    public ResponseDto<UserDto> patchUser(@RequestBody PatchUserDto requestBody, @AuthenticationPrincipal String userEmail){
        UserDto userDto = userService.patchUser(requestBody, userEmail);
        return ResponseDto.setSuccess(userDto);
    }

    @GetMapping("/myPage")
    public ResponseDto<MyPageDto> myPage(@AuthenticationPrincipal String userEmail){
        MyPageDto myPageDto = userService.myPage(userEmail);
        return ResponseDto.setSuccess(myPageDto);
    }
}
