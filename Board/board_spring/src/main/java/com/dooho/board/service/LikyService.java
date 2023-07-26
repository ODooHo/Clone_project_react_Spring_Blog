package com.dooho.board.service;

import com.dooho.board.dto.LikyDto;
import com.dooho.board.dto.ResponseDto;
import com.dooho.board.entity.BoardEntity;
import com.dooho.board.entity.LikyEntity;
import com.dooho.board.repository.BoardRepository;
import com.dooho.board.repository.LikyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        try{
            likyRepository.save(likyEntity);
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
    public ResponseDto<?> deleteLiky(Integer boardNumber) {
        try{
            likyRepository.deleteByBoardNumber(boardNumber);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error");
        }

        return ResponseDto.setSuccess("Success",null);
    }
}
