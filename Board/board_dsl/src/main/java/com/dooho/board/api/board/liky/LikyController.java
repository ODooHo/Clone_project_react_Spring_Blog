package com.dooho.board.api.board.liky;

import com.dooho.board.api.board.liky.LikyDto;
import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.board.liky.LikyEntity;
import com.dooho.board.api.board.liky.LikyService;
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


    @PostMapping("/{boardId}/liky/add")
    public ResponseDto<?>addLiky(@RequestBody LikyDto requestBody){
        ResponseDto<?> result = likyService.addLiky(requestBody);
        return result;
    }

    @GetMapping("/{boardId}/liky/get")
    public ResponseDto<List<LikyEntity>> getLiky(@PathVariable Integer boardId){
        ResponseDto<List<LikyEntity>> result =  likyService.getLiky(boardId);
        return result;
    }

    @GetMapping("/{boardId}/liky/get/count")
    public ResponseDto<Integer> getLikyCount(@PathVariable Integer boardId){
        ResponseDto<Integer> result =  likyService.getLikyCount(boardId);
        return result;
    }

    @GetMapping("/{boardId}/liky/delete/{likeUserNickname}")
    public ResponseDto<?> deleteLiky(@PathVariable Integer boardId,@PathVariable String likeUserNickname){
        ResponseDto<?> result = likyService.deleteLiky(boardId,likeUserNickname);
        return result;
    }

}
