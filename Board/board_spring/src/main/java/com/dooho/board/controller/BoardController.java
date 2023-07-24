package com.dooho.board.controller;

import com.dooho.board.dto.BoardDto;
import com.dooho.board.dto.ResponseDto;
import com.dooho.board.dto.SignUpDto;
import com.dooho.board.entity.BoardEntity;
import com.dooho.board.entity.PopularSearchEntity;
import com.dooho.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
public class BoardController {
    @Autowired
    BoardService boardService;

    @PostMapping("/register")
    public ResponseDto<?> register(@RequestBody BoardDto requestBody){
        ResponseDto<?> result = boardService.register(requestBody);
        System.out.println("requestBody = " + requestBody);
        return result;
    }

    @GetMapping("/top3")
    public ResponseDto<List<BoardEntity>> getTop3(){
        return boardService.getTop3();
    }

    @GetMapping("/list")
    public ResponseDto<List<BoardEntity>> getList(){
        return boardService.getList();
    }

    @GetMapping("/popularsearchList")
    public ResponseDto<List<PopularSearchEntity>> getPopularsearchList(){
        return boardService.getPopularsearchList();
    }

    @GetMapping("/search/{boardTitle}")
    public ResponseDto<List<BoardEntity>> getSearchList(@PathVariable("boardTitle") String title){
        return null;
    }

    @GetMapping("/{boardNumber}")
    public ResponseDto<BoardEntity> getBoard(@PathVariable Integer boardNumber){
        return boardService.getBoard(boardNumber);
    }

}
