package com.dooho.board.service;

import com.dooho.board.dto.board.LikyDto;
import com.dooho.board.dto.ResponseDto;
import com.dooho.board.entity.BoardEntity;
import com.dooho.board.entity.LikyEntity;
import com.dooho.board.repository.BoardRepository;
import com.dooho.board.repository.LikyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LikyService {

    private final LikyRepository likyRepository;
    private final BoardRepository boardRepository;

    @Autowired
    public LikyService(LikyRepository likyRepository, BoardRepository boardRepository) {
        this.likyRepository = likyRepository;
        this.boardRepository = boardRepository;
    }

    public ResponseDto<?> addLiky(LikyDto dto){
        LikyEntity likyEntity = new LikyEntity(dto);
        //List<LikyEntity> likyList = new ArrayList<>();
        likyRepository.save(likyEntity);
        try{

            // 해당 게시글의 좋아요 개수 증가
            BoardEntity board = boardRepository.findByBoardNumber(dto.getBoardNumber());
            if (board != null) {
                board.setBoardLikeCount(board.getBoardLikeCount() + 1);
                boardRepository.save(board);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error");
        }

        return ResponseDto.setSuccess("Success",null);
    }

    public ResponseDto<Integer> getLikyCount(Integer boardNumber){
        Integer likyCount = 0;
        BoardEntity boardEntity = boardRepository.findById(boardNumber).orElse(null);
        try{
            likyCount = likyRepository.countByBoardNumber(boardNumber);
            boardEntity.setBoardLikeCount(likyCount);
            boardRepository.save(boardEntity);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error");
        }

        return ResponseDto.setSuccess("Success",likyCount);
    }


    public ResponseDto<List<LikyEntity>> getLiky(Integer boardNumber){
        List<LikyEntity> likyEntity =  new ArrayList<>();
        try{
            likyEntity = likyRepository.findByBoardNumber(boardNumber);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error");
        }

        return ResponseDto.setSuccess("Success",likyEntity);
    }
    public ResponseDto<?> deleteLiky(Integer boardNumber, String likeUserNickname) {
        try{
            // 데이터베이스에서 사용자의 닉네임과 게시글 번호에 해당하는 좋아요 삭제
            likyRepository.deleteByBoardNumberAndLikeUserNickname(boardNumber, likeUserNickname);
            // 해당 게시글의 좋아요 개수 감소
            BoardEntity board = boardRepository.findByBoardNumber(boardNumber);
            if (board != null) {
                board.setBoardLikeCount(board.getBoardLikeCount() - 1);
                boardRepository.save(board);
            }

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error");
        }

        return ResponseDto.setSuccess("Success",null);
    }
}
