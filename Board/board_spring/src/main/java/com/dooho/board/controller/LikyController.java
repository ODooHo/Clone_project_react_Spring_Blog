package com.dooho.board.controller;

import com.dooho.board.dto.LikyDto;
import com.dooho.board.dto.ResponseDto;
import com.dooho.board.service.LikyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
public class LikyController {

    private final LikyService likyService;

    @Autowired
    public LikyController(LikyService likyService) {
        this.likyService = likyService;
    }


    @PostMapping("/{boardNumber}/liky/add")
    public ResponseDto<?>addLike(@RequestBody LikyDto requestBody){
        ResponseDto<?> result = likyService.addLike(requestBody);
        return result;
    }

    @GetMapping("/{boardNumber}/liky/get")
    public ResponseDto<Integer> getLiky(@PathVariable Integer boardNumber){
        ResponseDto<Integer> result =  likyService.getLiky(boardNumber);
        return result;
    }

}
