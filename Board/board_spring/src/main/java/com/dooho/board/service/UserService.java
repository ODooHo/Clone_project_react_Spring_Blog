package com.dooho.board.service;

import com.dooho.board.dto.MyPageDto;
import com.dooho.board.dto.PatchUserDto;
import com.dooho.board.dto.PatchUserResponseDto;
import com.dooho.board.dto.ResponseDto;
import com.dooho.board.entity.BoardEntity;
import com.dooho.board.entity.UserEntity;
import com.dooho.board.repository.BoardRepository;
import com.dooho.board.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Autowired
    public UserService(UserRepository userRepository, BoardRepository boardRepository) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
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
        dto.setUserNickname(user.getUserNickname());
        dto.setUserBoard(board);


        return ResponseDto.setSuccess("Success",dto);

    }

    public ResponseDto<PatchUserResponseDto> patchUser(PatchUserDto requestBody, String userEmail){

        UserEntity userEntity = null;
        List<BoardEntity> boardEntity = new ArrayList<>();

        String userNickname = requestBody.getUserNickname();
        String userProfile = requestBody.getUserProfile();



        try{
            userEntity = userRepository.findByUserEmail(userEmail);
            if (userEntity == null){
                return ResponseDto.setFailed("Does Not Exist User");
            }
            userEntity.setUserNickname(userNickname);
            userEntity.setUserProfile(userProfile);

            userRepository.save(userEntity);

            boardEntity = boardRepository.findByBoardWriterEmail(userEmail);

            for (BoardEntity board : boardEntity){
                board.setBoardWriterNickname(userNickname);
                boardRepository.save(board);
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
