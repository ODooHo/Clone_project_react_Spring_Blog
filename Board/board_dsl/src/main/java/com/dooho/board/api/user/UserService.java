package com.dooho.board.api.user;


import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.board.BoardRepository;
import com.dooho.board.api.board.dto.BoardDto;
import com.dooho.board.api.exception.BoardApplicationException;
import com.dooho.board.api.exception.ErrorCode;
import com.dooho.board.api.user.dto.MyPageDto;
import com.dooho.board.api.user.dto.PatchUserDto;
import com.dooho.board.api.user.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public UserService(UserRepository userRepository, BoardRepository boardRepository) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
    }


    @Transactional(readOnly = true)
    public MyPageDto myPage(String userEmail) {
        UserEntity user = userRepository.findById(userEmail).orElseThrow(
                () -> new BoardApplicationException(ErrorCode.USER_NOT_FOUND,String.format("userEmail is %s",userEmail))
        );
        List<BoardDto> board = boardRepository.findByUser_UserEmail(userEmail)
                .stream()
                .map(BoardDto::from)
                .toList();

        return MyPageDto.of(userEmail,user.getUserNickname(),user.getUserProfile(),board);
    }

    public UserDto patchUser(PatchUserDto requestBody, String userEmail) {
        String userNickname = requestBody.userNickname();
        UserEntity user = userRepository.findById(userEmail).orElseThrow(
                () -> new BoardApplicationException(ErrorCode.USER_NOT_FOUND,String.format("userEmail is %s",userEmail))
        );
        user.setUserNickname(userNickname);
        userRepository.save(user);
        user.setUserPassword("");

        return UserDto.from(user);
    }

}
