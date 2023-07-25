package com.dooho.board.service;

import com.dooho.board.dto.CommentDto;
import com.dooho.board.dto.ResponseDto;
import com.dooho.board.entity.BoardEntity;
import com.dooho.board.entity.CommentEntity;
import com.dooho.board.repository.BoardRepository;
import com.dooho.board.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, BoardRepository boardRepository) {
        this.commentRepository = commentRepository;
        this.boardRepository = boardRepository;
    }

    public ResponseDto<?> register(CommentDto dto){

        CommentEntity commentEntity = new CommentEntity(dto);

        try{
            commentRepository.save(commentEntity);
        }catch (Exception e){
            return ResponseDto.setFailed("DataBase Error!");
        }


        return ResponseDto.setSuccess("Success",null);

    }

    public ResponseDto<List<CommentEntity>> getComment(Integer boardNumber){
        List<CommentEntity> commentList = new ArrayList<>();

        BoardEntity boardEntity = boardRepository.findById(boardNumber).orElse(null);
        Integer commentCount = 0;

        try{
            commentList = commentRepository.findByBoardNumberOrderByCommentWriteDateDesc(boardNumber);
            commentCount = commentRepository.countByBoardNumber(boardNumber);

            boardEntity.setBoardCommentCount(commentCount);
            boardRepository.save(boardEntity);

        }catch (Exception e){
            return ResponseDto.setFailed("DataBase Error!");
        }

        return ResponseDto.setSuccess("Success",commentList);


    }



}
