package com.dooho.board.api.comment;


import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.comment.dto.CommentDto;
import com.dooho.board.api.comment.dto.PatchCommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/board")
public class CommentController {
    private final CommentService commentService;


    @PostMapping("/{boardId}/comment/register")
    public ResponseDto<Void> register(@AuthenticationPrincipal String userEmail, @PathVariable Integer boardId , @RequestBody CommentDto requestBody){
        commentService.register(userEmail,boardId,requestBody);
        return ResponseDto.setSuccess();
    }


    @GetMapping("/{boardId}/comment/list")
    public ResponseDto<List<CommentDto>> getComment(@PathVariable Integer boardId){
        List<CommentDto> comment = commentService.getComment(boardId);
        return ResponseDto.setSuccess(comment);
    }

    @GetMapping("/comment/{commentId}/delete")
    public ResponseDto<Void> deleteComment(@PathVariable Integer commentId){
        commentService.deleteComment(commentId);
        return ResponseDto.setSuccess();
    }

    @CrossOrigin(origins = "*")
    @PatchMapping("/{boardId}/comment/{commentId}/edit")
    public ResponseDto<CommentDto> editComment(
            @PathVariable Integer commentId,
            @RequestBody PatchCommentDto requestBody){
        CommentDto commentDto = commentService.editComment(commentId, requestBody);
        return ResponseDto.setSuccess(commentDto);
    }


}
