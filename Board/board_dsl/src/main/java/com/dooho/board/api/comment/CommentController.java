package com.dooho.board.api.comment;


import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.comment.dto.CommentDto;
import com.dooho.board.api.comment.dto.PatchCommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/board")
public class CommentController {
    private final CommentService commentService;


    @PostMapping("/{boardId}/comment/register")
    public ResponseEntity<ResponseDto<String>> register(@AuthenticationPrincipal String userEmail, @PathVariable Integer boardId , @RequestBody CommentDto requestBody){
        return commentService.register(userEmail,boardId,requestBody);
    }


    @GetMapping("/{boardId}/comment/list")
    public ResponseEntity<ResponseDto<List<CommentDto>>> getComment(@PathVariable Integer boardId){
        return commentService.getComment(boardId);
    }

    @GetMapping("/comment/{commentId}/delete")
    public ResponseEntity<ResponseDto<String>> deleteComment(@PathVariable Integer commentId){
        return commentService.deleteComment(commentId);
    }

    @CrossOrigin(origins = "*")
    @PatchMapping("{boardId}/comment/{commentId}/edit")
    public ResponseEntity<ResponseDto<CommentDto>> editComment(
            @PathVariable Integer boardId,
            @PathVariable Integer commentId,
            @RequestBody PatchCommentDto requestBody){
        return commentService.editComment(boardId,commentId,requestBody);
    }


}
