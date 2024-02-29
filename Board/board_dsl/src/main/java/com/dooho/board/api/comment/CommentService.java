package com.dooho.board.api.comment;

import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.board.BoardEntity;
import com.dooho.board.api.board.BoardRepository;
import com.dooho.board.api.comment.dto.CommentDto;
import com.dooho.board.api.comment.dto.PatchCommentDto;
import com.dooho.board.api.exception.BoardApplicationException;
import com.dooho.board.api.exception.ErrorCode;
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


    public void register(String userEmail, Integer boardId, CommentDto dto) {
        UserEntity user = userRepository.getReferenceById(userEmail);
        BoardEntity board = boardRepository.getReferenceById(boardId);
        CommentEntity commentEntity = CommentEntity.of(null, LocalDate.now(), dto.commentContent(), board, user);
        commentRepository.save(commentEntity);
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getComment(Integer boardId) {
        return commentRepository.findAllByBoard_Id(boardId)
                .stream()
                .map(CommentDto::from)
                .toList();
    }

    public CommentDto editComment(Integer commentId, PatchCommentDto dto) {
        String commentContent = dto.commentContent();
        LocalDate commentWriteDate = dto.commentWriteDate();
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(
                () -> new BoardApplicationException(ErrorCode.COMMENT_NOT_FOUND,String.format("commentId is %d",commentId))
        );
        comment.setCommentContent(commentContent);
        comment.setCommentWriteDate(commentWriteDate);

        commentRepository.save(comment);
        return CommentDto.from(comment);
    }


    public void deleteComment(Integer commentId) {
        commentRepository.deleteById(commentId);
    }


}
