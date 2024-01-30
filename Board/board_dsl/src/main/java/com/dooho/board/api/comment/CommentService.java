package com.dooho.board.api.comment;

import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.board.BoardEntity;
import com.dooho.board.api.board.BoardRepository;
import com.dooho.board.api.comment.dto.CommentDto;
import com.dooho.board.api.comment.dto.PatchCommentDto;
import com.dooho.board.api.comment.dto.PatchCommentResponseDto;
import com.dooho.board.api.user.UserEntity;
import com.dooho.board.api.user.UserRepository;
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
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, BoardRepository boardRepository,
                          UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }


    public ResponseDto<?> register(String userEmail, Integer boardId, CommentDto dto) {
        UserEntity user = userRepository.getReferenceById(userEmail);
        BoardEntity board = boardRepository.getReferenceById(boardId);
        CommentEntity commentEntity = CommentEntity.of(null,LocalDate.now(),dto.commentContent(),board,user);
        try {
            commentRepository.save(commentEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error!");
        }


        return ResponseDto.setSuccess("Success", null);

    }

    @Transactional(readOnly = true)
    public ResponseDto<List<CommentEntity>> getComment(Integer boardId) {
        List<CommentEntity> commentList = new ArrayList<>();
        System.out.println(commentList);
        try {
            commentList = commentRepository.findAllByBoard_Id(boardId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error!");
        }
        return ResponseDto.setSuccess("Success", commentList);
    }

    public ResponseDto<PatchCommentResponseDto> editComment(Integer boardId, Integer commentId, PatchCommentDto dto) {
        CommentEntity comment = null;
        String commentContent = dto.getCommentContent();
        LocalDate commentWriteDate = dto.getCommentWriteDate();

        try {
            comment = commentRepository.findById(commentId).orElse(null);
            comment.setCommentContent(commentContent);
            comment.setCommentWriteDate(commentWriteDate);

            commentRepository.save(comment);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error!");
        }

        PatchCommentResponseDto patchCommentResponseDto = new PatchCommentResponseDto(comment);

        return ResponseDto.setSuccess("Success!", patchCommentResponseDto);
    }


    public ResponseDto<?> deleteComment(Integer boardId, Integer commentId) {
        BoardEntity boardEntity = boardRepository.findById(boardId).orElse(null);
        try {
            commentRepository.deleteById(commentId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error!");
        }
        return ResponseDto.setSuccess("Success", null);
    }


}
