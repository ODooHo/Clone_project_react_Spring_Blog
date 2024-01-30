package com.dooho.board.api.board;

import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.board.PatchBoardDto;
import com.dooho.board.api.board.PatchBoardResponseDto;
import com.dooho.board.api.board.BoardEntity;
import com.dooho.board.api.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/register")
    public ResponseDto<BoardEntity> register(
            @AuthenticationPrincipal String userEmail,
            @RequestParam("boardTitle") String boardTitle,
            @RequestParam("boardContent") String boardContent,
            @RequestParam("boardWriteDate") String boardWriteDate,
            @RequestParam(value = "boardImage", required = false) MultipartFile boardImage,
            @RequestParam(value = "boardVideo", required = false) MultipartFile boardVideo,
            @RequestParam(value = "boardFile", required = false) MultipartFile boardFile){
        ResponseDto<BoardEntity> result = boardService.register(userEmail,
                boardTitle, boardContent,boardWriteDate,
                boardImage,boardVideo,boardFile);
        return result;
    }

    @GetMapping("/top3")
    public ResponseDto<List<BoardEntity>> getTop3(){
        ResponseDto<List<BoardEntity>> result = boardService.getTop3();
        return result;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/list")
    public ResponseDto<List<BoardEntity>> getList(){
        ResponseDto<List<BoardEntity>> result = boardService.getList();
        return result;
    }


    @CrossOrigin(origins = "*")
    @GetMapping("/{boardId}")
    public ResponseDto<BoardEntity> getBoard(@PathVariable Integer boardId){
        ResponseDto<BoardEntity> result = boardService.getBoard(boardId);
        return result;
    }

    @PostMapping("/{boardId}")
    public ResponseDto<?>increaseView(@PathVariable Integer boardId, @RequestBody Integer requestBody){
        ResponseDto<?> result = boardService.increaseView(boardId,requestBody);
        return result;
    }


    @GetMapping  ("/{boardId}/delete")
    public ResponseDto<?> deleteBoard(@PathVariable Integer boardId){
        ResponseDto<?> result = boardService.deleteBoard(boardId);
        return result;
    }

    @CrossOrigin(origins = "*")
    @PatchMapping("{boardId}/edit")
    public ResponseDto<PatchBoardResponseDto> editBoard(
            @PathVariable Integer boardId,
            @RequestBody PatchBoardDto requestBody){
        ResponseDto<PatchBoardResponseDto> result = boardService.editBoard(boardId,requestBody);

        return result;
    }
}

