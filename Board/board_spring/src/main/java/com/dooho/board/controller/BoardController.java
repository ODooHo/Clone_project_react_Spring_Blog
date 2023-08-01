package com.dooho.board.controller;

import com.dooho.board.dto.ResponseDto;
import com.dooho.board.dto.board.PatchBoardDto;
import com.dooho.board.dto.board.PatchBoardResponseDto;
import com.dooho.board.entity.BoardEntity;
import com.dooho.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
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
            @RequestParam("boardTitle") String boardTitle,
            @RequestParam("boardContent") String boardContent,
            @RequestParam("boardWriterEmail") String boardWriterEmail,
            @RequestParam("boardWriterProfile") String boardWriterProfile,
            @RequestParam("boardWriterNickname") String boardWriterNickname,
            @RequestParam("boardWriteDate") String boardWriteDate,
            @RequestParam(value = "boardImage", required = false) MultipartFile boardImage,
            @RequestParam(value = "boardVideo", required = false) MultipartFile boardVideo,
            @RequestParam(value = "boardFile", required = false) MultipartFile boardFile){
        ResponseDto<BoardEntity> result = boardService.register(
                boardTitle, boardContent,boardWriterEmail,boardWriterProfile,boardWriterNickname,boardWriteDate,
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
    @GetMapping("/{boardNumber}")
    public ResponseDto<BoardEntity> getBoard(@PathVariable Integer boardNumber){
        ResponseDto<BoardEntity> result = boardService.getBoard(boardNumber);
        return result;
    }

    @PostMapping("/{boardNumber}")
    public ResponseDto<?>increaseView(@PathVariable Integer boardNumber, @RequestBody Integer requestBody){
        ResponseDto<?> result = boardService.increaseView(boardNumber,requestBody);
        return result;
    }


    @GetMapping  ("/{boardNumber}/delete")
    public ResponseDto<?> deleteBoard(@PathVariable Integer boardNumber){
        ResponseDto<?> result = boardService.deleteBoard(boardNumber);
        return result;
    }

    @CrossOrigin(origins = "*")
    @PatchMapping("{boardNumber}/edit")
    public ResponseDto<PatchBoardResponseDto> editBoard(
            @PathVariable Integer boardNumber,
            @RequestBody PatchBoardDto requestBody){
        ResponseDto<PatchBoardResponseDto> result = boardService.editBoard(boardNumber,requestBody);

        return result;
    }
}

