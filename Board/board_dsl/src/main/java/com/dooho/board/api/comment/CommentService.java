package com.dooho.board.api.comment;

import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.board.BoardEntity;
import com.dooho.board.api.board.BoardRepository;
import com.dooho.board.api.comment.dto.CommentDto;
import com.dooho.board.api.comment.dto.PatchCommentDto;
import com.dooho.board.api.user.UserEntity;
import com.dooho.board.api.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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


    public ResponseEntity<ResponseDto<String>> register(String userEmail, Integer boardId, CommentDto dto) {
        UserEntity user = userRepository.getReferenceById(userEmail);
        BoardEntity board = boardRepository.getReferenceById(boardId);
        CommentEntity commentEntity = CommentEntity.of(null, LocalDate.now(), dto.commentContent(), board, user);
        commentRepository.save(commentEntity);


        return ResponseDto.setSuccess("Success", "Success");

    }

    @Transactional(readOnly = true)
    public ResponseEntity<ResponseDto<List<CommentDto>>> getComment(Integer boardId) {
        List<CommentDto> commentList = commentRepository.findAllByBoard_Id(boardId)
                .stream()
                .map(CommentDto::from)
                .toList();
        return ResponseDto.setSuccess("Success", commentList);
    }

    public ResponseEntity<ResponseDto<CommentDto>> editComment(Integer boardId, Integer commentId, PatchCommentDto dto) {
        CommentEntity comment = null;
        String commentContent = dto.commentContent();
        LocalDate commentWriteDate = dto.commentWriteDate();

        comment = commentRepository.findById(commentId).orElse(null);
        comment.setCommentContent(commentContent);
        comment.setCommentWriteDate(commentWriteDate);

        commentRepository.save(comment);

        CommentDto response = CommentDto.from(comment);
        return ResponseDto.setSuccess("Success!", response);
    }


    public ResponseEntity<ResponseDto<String>> deleteComment(Integer commentId) {
        commentRepository.deleteById(commentId);
        return ResponseDto.setSuccess("Success", "Delete Comment Success");
    }


}
