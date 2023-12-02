package com.dooho.board.api.user;


import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.board.BoardEntity;
import com.dooho.board.api.comment.CommentEntity;
import com.dooho.board.api.board.BoardRepository;
import com.dooho.board.api.comment.CommentRepository;
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
            user = userRepository.findById(userEmail).orElse(null);
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
            userEntity = userRepository.findById(userEmail).orElse(null);
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
