package com.dooho.board.api.comment;


import com.dooho.board.api.comment.CommentDto;
import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.comment.PatchCommentDto;
import com.dooho.board.api.comment.PatchCommentResponseDto;
import com.dooho.board.api.comment.CommentEntity;
import com.dooho.board.api.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{boardId}/comment/register")
    public ResponseDto<?> register(@RequestBody CommentDto requestBody){
        ResponseDto<?> result = commentService.register(requestBody);
        return result;
    }


    @GetMapping("/{boardId}/comment/list")
    public ResponseDto<List<CommentEntity>> getComment(@PathVariable Integer boardId){
        ResponseDto<List<CommentEntity>> result = commentService.getComment(boardId);
        return result;
    }

    @GetMapping("/{boardId}/comment/{commentId}/delete")
    public ResponseDto<?> deleteComment(@PathVariable Integer boardId, @PathVariable Integer commentId){
        ResponseDto<?> result = commentService.deleteComment(boardId, commentId);
        return result;
    }

    @CrossOrigin(origins = "*")
    @PatchMapping("{boardId}/comment/{commentId}/edit")
    public ResponseDto<PatchCommentResponseDto> editComment(
            @PathVariable Integer boardId,
            @PathVariable Integer commentId,
            @RequestBody PatchCommentDto requestBody){
        ResponseDto<PatchCommentResponseDto> result = commentService.editComment(boardId,commentId,requestBody);
        return result;
    }


}
