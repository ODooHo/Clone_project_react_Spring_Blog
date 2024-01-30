package com.dooho.board.api.user;


import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.board.BoardEntity;
import com.dooho.board.api.board.BoardRepository;
import com.dooho.board.api.comment.CommentEntity;
import com.dooho.board.api.comment.CommentRepository;
import com.dooho.board.api.user.dto.MyPageDto;
import com.dooho.board.api.user.dto.PatchUserDto;
import com.dooho.board.api.user.dto.PatchUserResponseDto;
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
        List<BoardEntity> board = new ArrayList<>();
        try{
            user = userRepository.findById(userEmail).orElse(null);
            board = boardRepository.findByUser_UserEmail(userEmail);
        }catch (Exception e){
            return ResponseDto.setFailed("Does Not Exist User");
        }

        MyPageDto dto = new MyPageDto();
        dto.setUserEmail(userEmail);
        dto.setUserNickname(user.getUserNickname());
        dto.setUserProfile(user.getUserProfile());
        dto.setUserBoard(board);



        return ResponseDto.setSuccess("Success",dto);

    }

    public ResponseDto<PatchUserResponseDto> patchUser(PatchUserDto requestBody, String userEmail){

        UserEntity userEntity = null;
        List<BoardEntity> boardEntity = new ArrayList<>();
        List<CommentEntity> commentEntity = new ArrayList<>();

        String userNickname = requestBody.getUserNickname();


        try{
            userEntity = userRepository.findById(userEmail).orElse(null);
            commentEntity = commentRepository.findByUser_UserEmail(userEmail);
            boardEntity = boardRepository.findByUser_UserEmail(userEmail);
            if (userEntity == null){
                return ResponseDto.setFailed("Does Not Exist User");
            }
            userEntity.setUserNickname(userNickname);

            userRepository.save(userEntity);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error!");
        }

        userEntity.setUserPassword("");

        PatchUserResponseDto patchUserResponseDto = new PatchUserResponseDto(userEntity);


        return ResponseDto.setSuccess("Success",patchUserResponseDto);
    }

}
