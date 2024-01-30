package com.dooho.board.api.comment;

import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.board.BoardEntity;
import com.dooho.board.api.board.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Transactional
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
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error!");
        }


        return ResponseDto.setSuccess("Success",null);

    }

    @Transactional(readOnly = true)
    public ResponseDto<List<CommentEntity>> getComment(Integer boardId){
        List<CommentEntity> commentList = new ArrayList<>();

        BoardEntity boardEntity = boardRepository.findById(boardId).orElse(null);
        Integer commentCount = 0;

        try{
            commentList = commentRepository.getComment(boardId);
            commentCount = commentRepository.countByboardId(boardId);

            boardEntity.setCommentCount(commentCount);
            boardRepository.save(boardEntity);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error!");
        }

        return ResponseDto.setSuccess("Success",commentList);


    }

    public ResponseDto<PatchCommentResponseDto> editComment(Integer boardId, Integer commentId, PatchCommentDto dto) {
        CommentEntity comment = null;
        String commentContent = dto.getCommentContent();
        LocalDate commentWriteDate = dto.getCommentWriteDate();

        try{
            comment = commentRepository.findById(commentId).orElse(null);
            comment.setCommentContent(commentContent);
            comment.setCommentWriteDate(commentWriteDate);

            commentRepository.save(comment);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error!");
        }

        PatchCommentResponseDto patchCommentResponseDto = new PatchCommentResponseDto(comment);

        return ResponseDto.setSuccess("Success!",patchCommentResponseDto);
    }



    public ResponseDto<?> deleteComment(Integer boardId, Integer commentId) {
        BoardEntity boardEntity = boardRepository.findById(boardId).orElse(null);
        try{
            commentRepository.deleteByCommentId(commentId);
            Integer temp = boardEntity.getCommentCount();
            boardEntity.setCommentCount(temp - 1);
            boardRepository.save(boardEntity);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error!");
        }
        return ResponseDto.setSuccess("Success",null);
    }



}
