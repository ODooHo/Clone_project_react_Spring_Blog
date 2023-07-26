package com.dooho.board.controller;

import com.dooho.board.dto.CommentDto;
import com.dooho.board.dto.ResponseDto;
import com.dooho.board.entity.CommentEntity;
import com.dooho.board.service.CommentService;
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

    @PostMapping("/{boardNumber}/comment/register")
    public ResponseDto<?> register(@RequestBody CommentDto requestBody){
        return commentService.register(requestBody);
    }


    @GetMapping("/{boardNumber}/comment/list")
    public ResponseDto<List<CommentEntity>> getComment(@PathVariable Integer boardNumber){
        return commentService.getComment(boardNumber);
    }

    @GetMapping("/{boardNumber}/comment/{commentId}/delete")
    public ResponseDto<?> deleteComment(@PathVariable Integer boardNumber, @PathVariable Integer commentId){
        return commentService.deleteComment(boardNumber,commentId);
    }


}
