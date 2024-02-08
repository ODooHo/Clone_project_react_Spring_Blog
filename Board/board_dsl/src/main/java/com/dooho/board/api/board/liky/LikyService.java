package com.dooho.board.api.board.liky;

import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.board.BoardEntity;
import com.dooho.board.api.board.BoardRepository;
import com.dooho.board.api.board.liky.dto.LikyDto;
import com.dooho.board.api.user.UserEntity;
import com.dooho.board.api.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public ResponseEntity<ResponseDto<String>> like(String userEmail, Integer boardId){
        LikyEntity check = likyRepository.findByBoard_IdAndUser_UserEmail(boardId, userEmail);
        if(check == null){
            UserEntity user = userRepository.getReferenceById(userEmail);
            BoardEntity board = boardRepository.getReferenceById(boardId);
            LikyEntity liky = LikyEntity.of(null,board, user);
            LikyDto response = LikyDto.from(liky);
            likyRepository.save(liky);
            return ResponseDto.setSuccess("Success", "Add Like Success");
        }else{
            likyRepository.deleteById(check.getId());
            return ResponseDto.setSuccess("Success", "Delete Like Success");
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ResponseDto<Integer>> getLikyCount(Integer boardId){
        Integer temp = likyRepository.countByBoard_Id(boardId);
        return ResponseDto.setSuccess("Success",temp);
    }


    public ResponseEntity<ResponseDto<List<LikyDto>>> getLiky(Integer boardId){
        List<LikyDto> likeList = likyRepository.findByBoard_Id(boardId)
                .stream()
                .map(LikyDto::from)
                .toList();
        return ResponseDto.setSuccess("Success",likeList);
    }

}
