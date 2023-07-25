package com.dooho.board.controller;

import com.dooho.board.dto.BoardDto;
import com.dooho.board.dto.ResponseDto;
import com.dooho.board.entity.BoardEntity;
import com.dooho.board.entity.PopularSearchEntity;
import com.dooho.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResponseDto<?> register(@RequestBody BoardDto requestBody){
        ResponseDto<?> result = boardService.register(requestBody);
        System.out.println("requestBody = " + requestBody);
        return result;
    }

    @GetMapping("/top3")
    public ResponseDto<List<BoardEntity>> getTop3(){
        ResponseDto<List<BoardEntity>> result = boardService.getTop3();
        return result;
    }

    @GetMapping("/list")
    public ResponseDto<List<BoardEntity>> getList(){
        ResponseDto<List<BoardEntity>> result = boardService.getList();
        return result;
    }

    @GetMapping("/popularsearchList")
    public ResponseDto<List<PopularSearchEntity>> getPopularsearchList(){
        ResponseDto<List<PopularSearchEntity>> result = boardService.getPopularsearchList();
        return result;
    }

    @GetMapping("/search/{boardTitle}")
    public ResponseDto<List<BoardEntity>> getSearchList(@PathVariable("boardTitle") String title){
        return null;
    }

    @GetMapping("/{boardNumber}")
    public ResponseDto<BoardEntity> getBoard(@PathVariable Integer boardNumber){
        ResponseDto<BoardEntity> result = boardService.getBoard(boardNumber);
        return result;
    }

}
