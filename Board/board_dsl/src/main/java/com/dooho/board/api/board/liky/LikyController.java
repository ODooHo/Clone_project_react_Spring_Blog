//package com.dooho.board.api.board.liky;
//
//import com.dooho.board.api.board.liky.dto.LikyDto;
//import com.dooho.board.api.ResponseDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/api/board")
//public class LikyController {
//
//    private final LikyService likyService;
//
//
//    @PostMapping("/{boardId}/liky/add")
//    public ResponseDto<?>addLiky(@RequestBody LikyDto requestBody){
//        return likyService.addLiky(requestBody);
//    }
//
//    @GetMapping("/{boardId}/liky/get")
//    public ResponseDto<List<LikyEntity>> getLiky(@PathVariable Integer boardId){
//        return likyService.getLiky(boardId);
//    }
//
//    @GetMapping("/{boardId}/liky/get/count")
//    public ResponseDto<Integer> getLikyCount(@PathVariable Integer boardId){
//        return likyService.getLikyCount(boardId);
//    }
//
//    @GetMapping("/{boardId}/liky/delete/{likeUserNickname}")
//    public ResponseDto<?> deleteLiky(@PathVariable Integer boardId,@PathVariable String likeUserNickname){
//        return likyService.deleteLiky(boardId,likeUserNickname);
//    }
//
//}
