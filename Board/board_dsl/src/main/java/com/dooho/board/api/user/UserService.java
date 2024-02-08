package com.dooho.board.api.user;


import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.board.BoardRepository;
import com.dooho.board.api.board.dto.BoardDto;
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
    public ResponseEntity<ResponseDto<MyPageDto>> myPage(String userEmail) {
        UserEntity user = null;
        user = userRepository.findById(userEmail).orElse(null);
        if(user == null){
            throw new BadCredentialsException("UnAuthorized Request");
        }
        List<BoardDto> board = boardRepository.findByUser_UserEmail(userEmail)
                .stream()
                .map(BoardDto::from)
                .toList();
        MyPageDto dto = MyPageDto.of(userEmail,user.getUserNickname(),user.getUserProfile(),board);

        return ResponseDto.setSuccess("Success", dto);
    }

    public ResponseEntity<ResponseDto<UserDto>> patchUser(PatchUserDto requestBody, String userEmail) {

        UserEntity userEntity = null;
        String userNickname = requestBody.userNickname();

        userEntity = userRepository.findById(userEmail).orElse(null);
        if (userEntity == null) {
            throw new BadCredentialsException("UnAuthorized Request");
        }
        userEntity.setUserNickname(userNickname);

        userRepository.save(userEntity);

        userEntity.setUserPassword("");

        UserDto response = UserDto.from(userEntity);

        return ResponseDto.setSuccess("Success", response);
    }

}
