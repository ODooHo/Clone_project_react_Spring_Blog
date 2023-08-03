package com.dooho.board.controller;

import com.dooho.board.dto.board.LikyDto;
import com.dooho.board.dto.ResponseDto;
import com.dooho.board.entity.LikyEntity;
import com.dooho.board.service.LikyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
public class LikyController {

    private final LikyService likyService;

    @Autowired
    public LikyController(LikyService likyService) {
        this.likyService = likyService;
    }


    @PostMapping("/{boardNumber}/liky/add")
    public ResponseDto<?>addLiky(@RequestBody LikyDto requestBody){
        ResponseDto<?> result = likyService.addLiky(requestBody);
        return result;
    }

    @GetMapping("/{boardNumber}/liky/get")
    public ResponseDto<List<LikyEntity>> getLiky(@PathVariable Integer boardNumber){
        ResponseDto<List<LikyEntity>> result =  likyService.getLiky(boardNumber);
        return result;
    }

    @GetMapping("/{boardNumber}/liky/get/count")
    public ResponseDto<Integer> getLikyCount(@PathVariable Integer boardNumber){
        ResponseDto<Integer> result =  likyService.getLikyCount(boardNumber);
        return result;
    }

    @GetMapping("/{boardNumber}/liky/delete/{likeUserNickname}")
    public ResponseDto<?> deleteLiky(@PathVariable Integer boardNumber,@PathVariable String likeUserNickname){
        ResponseDto<?> result = likyService.deleteLiky(boardNumber,likeUserNickname);
        return result;
    }

}
