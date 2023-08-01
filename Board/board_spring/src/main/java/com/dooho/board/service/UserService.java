package com.dooho.board.service;

import com.dooho.board.dto.user.MyPageDto;
import com.dooho.board.dto.user.PatchUserDto;
import com.dooho.board.dto.user.PatchUserResponseDto;
import com.dooho.board.dto.ResponseDto;
import com.dooho.board.entity.BoardEntity;
import com.dooho.board.entity.CommentEntity;
import com.dooho.board.entity.UserEntity;
import com.dooho.board.repository.BoardRepository;
import com.dooho.board.repository.CommentRepository;
import com.dooho.board.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public UserService(UserRepository userRepository, BoardRepository boardRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.commentRepository = commentRepository;
    }


    public ResponseDto<MyPageDto> myPage(String userEmail) {

        UserEntity user = null;
        List<BoardEntity> board = new ArrayList<>();

        try{
            user = userRepository.findByUserEmail(userEmail);
            board = boardRepository.findByBoardWriterEmail(userEmail);
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
            userEntity = userRepository.findByUserEmail(userEmail);
            commentEntity = commentRepository.findByUserEmail(userEmail);
            boardEntity = boardRepository.findByBoardWriterEmail(userEmail);
            if (userEntity == null){
                return ResponseDto.setFailed("Does Not Exist User");
            }
            userEntity.setUserNickname(userNickname);

            userRepository.save(userEntity);


            for (BoardEntity board : boardEntity){
                board.setBoardWriterNickname(userNickname);
                boardRepository.save(board);
            }

            for (CommentEntity comment : commentEntity){
                comment.setCommentUserNickname(userNickname);
                commentRepository.save(comment);
            }

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error!");
        }

        userEntity.setUserPassword("");

        PatchUserResponseDto patchUserResponseDto = new PatchUserResponseDto(userEntity);


        return ResponseDto.setSuccess("Success",patchUserResponseDto);
    }

}
