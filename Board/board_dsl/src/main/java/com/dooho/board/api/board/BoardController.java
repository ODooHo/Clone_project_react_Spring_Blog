package com.dooho.board.api.board;

import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.board.dto.BoardDto;
import com.dooho.board.api.board.dto.BoardWithCommentDto;
import com.dooho.board.api.board.dto.PatchBoardDto;
import com.dooho.board.api.board.dto.PatchBoardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/register")
    public ResponseDto<BoardEntity> register(
            @AuthenticationPrincipal String userEmail,
            @RequestParam("boardTitle") String boardTitle,
            @RequestParam("boardContent") String boardContent,
            @RequestParam(value = "boardImage", required = false) MultipartFile boardImage,
            @RequestParam(value = "boardVideo", required = false) MultipartFile boardVideo,
            @RequestParam(value = "boardFile", required = false) MultipartFile boardFile){
        return boardService.register(userEmail,
                boardTitle, boardContent,
                boardImage,boardVideo,boardFile);
    }

    @GetMapping("/top3")
    public ResponseDto<List<BoardDto>> getTop3(){
        return boardService.getTop3();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/list")
    public ResponseDto<List<BoardDto>> getList(){
        return boardService.getList();
    }


    @CrossOrigin(origins = "*")
    @GetMapping("/{boardId}")
    public ResponseDto<BoardWithCommentDto> getBoardWithComments(@PathVariable Integer boardId){
        return boardService.getBoardWithComments(boardId);
    }

    @PostMapping("/{boardId}")
    public ResponseDto<?>increaseView(@PathVariable Integer boardId, @RequestBody Integer requestBody){
        return boardService.increaseView(boardId,requestBody);
    }


    @GetMapping  ("/{boardId}/delete")
    public ResponseDto<?> deleteBoard(@PathVariable Integer boardId){
        return boardService.deleteBoard(boardId);
    }

    @CrossOrigin(origins = "*")
    @PatchMapping("{boardId}/edit")
    public ResponseDto<BoardDto> editBoard(
            @PathVariable Integer boardId,
            @RequestBody PatchBoardDto requestBody){
        return boardService.editBoard(boardId,requestBody);
    }
}

