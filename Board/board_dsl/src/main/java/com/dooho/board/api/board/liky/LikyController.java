package com.dooho.board.api.board.liky;

import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.board.liky.dto.LikyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/board")
public class LikyController {

    private final LikyService likyService;


    @GetMapping("/{boardId}/liky")
    public ResponseDto<String> like(@AuthenticationPrincipal String userEmail, @PathVariable Integer boardId){
        String response = likyService.like(userEmail, boardId);
        return ResponseDto.setSuccess(response);
    }

    @GetMapping("/{boardId}/liky/get")
    public ResponseDto<List<LikyDto>> getLiky(@PathVariable Integer boardId){
        List<LikyDto> response = likyService.getLiky(boardId);
        return ResponseDto.setSuccess(response);
    }

    @GetMapping("/{boardId}/liky/get/count")
    public ResponseDto<Integer> getLikyCount(@PathVariable Integer boardId){
        Integer response = likyService.getLikyCount(boardId);
        return ResponseDto.setSuccess(response);
    }


}
