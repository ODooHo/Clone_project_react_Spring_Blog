package com.dooho.board.api.user;


import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.board.BoardEntity;
import com.dooho.board.api.board.BoardRepository;
import com.dooho.board.api.board.dto.BoardDto;
import com.dooho.board.api.comment.CommentEntity;
import com.dooho.board.api.comment.CommentRepository;
import com.dooho.board.api.user.dto.MyPageDto;
import com.dooho.board.api.user.dto.PatchUserDto;
import com.dooho.board.api.user.dto.PatchUserResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public UserService(UserRepository userRepository, BoardRepository boardRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.commentRepository = commentRepository;
    }


    @Transactional(readOnly = true)
    public ResponseDto<MyPageDto> myPage(String userEmail) {
        UserEntity user = null;
        user = userRepository.findById(userEmail).orElse(null);
        if(user == null){
            throw new BadCredentialsException("UnAuthorized Request");
        }
        List<BoardDto> board = boardRepository.findByUser_UserEmail(userEmail)
                .stream()
                .map(BoardDto::from)
                .toList();
        MyPageDto dto = new MyPageDto();
        dto.setUserEmail(userEmail);
        dto.setUserNickname(user.getUserNickname());
        dto.setUserProfile(user.getUserProfile());
        dto.setUserBoard(board);

        return ResponseDto.setSuccess("Success", dto);
    }

    public ResponseDto<PatchUserResponseDto> patchUser(PatchUserDto requestBody, String userEmail) {

        UserEntity userEntity = null;
        String userNickname = requestBody.getUserNickname();

        userEntity = userRepository.findById(userEmail).orElse(null);
        if (userEntity == null) {
            throw new BadCredentialsException("UnAuthorized Request");
        }
        userEntity.setUserNickname(userNickname);

        userRepository.save(userEntity);

        userEntity.setUserPassword("");

        PatchUserResponseDto patchUserResponseDto = new PatchUserResponseDto(userEntity);


        return ResponseDto.setSuccess("Success", patchUserResponseDto);
    }

}
