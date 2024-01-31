package com.dooho.board.api.board.liky;

import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.board.BoardEntity;
import com.dooho.board.api.board.BoardRepository;
import com.dooho.board.api.board.liky.dto.LikyDto;
import com.dooho.board.api.user.UserEntity;
import com.dooho.board.api.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class LikyService {

    private final LikyRepository likyRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public LikyService(LikyRepository likyRepository, BoardRepository boardRepository,
                       UserRepository userRepository) {
        this.likyRepository = likyRepository;
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    public ResponseDto<?> like(String userEmail, Integer boardId){
        LikyEntity check = likyRepository.findByBoard_IdAndUser_UserEmail(boardId, userEmail);
        if(check == null){
            UserEntity user = userRepository.getReferenceById(userEmail);
            BoardEntity board = boardRepository.getReferenceById(boardId);
            LikyEntity liky = LikyEntity.of(null,board, user);
            LikyDto response = LikyDto.from(liky);
            likyRepository.save(liky);
            return ResponseDto.setSuccess("Success", response);
        }else{
            likyRepository.deleteById(check.getId());
            return ResponseDto.setSuccess("Success", "Delete Like");
        }
    }

    @Transactional(readOnly = true)
    public ResponseDto<Integer> getLikyCount(Integer boardId){
        BoardEntity boardEntity = boardRepository.findById(boardId).orElse(null);
        Integer temp = 0;
        try{
            temp = likyRepository.countByBoard_Id(boardId);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error");
        }

        return ResponseDto.setSuccess("Success",temp);
    }


    public ResponseDto<List<LikyDto>> getLiky(Integer boardId){
        List<LikyDto> likeList = likyRepository.findByBoard_Id(boardId)
                .stream()
                .map(LikyDto::from)
                .toList();
        return ResponseDto.setSuccess("Success",likeList);
    }

}
