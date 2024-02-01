package com.dooho.board.api.board;

import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.board.dto.BoardDetailDto;
import com.dooho.board.api.board.dto.BoardDto;
import com.dooho.board.api.board.dto.PatchBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/register")
    public ResponseDto<String> register(
            @AuthenticationPrincipal String userEmail,
            @RequestParam("boardTitle") String boardTitle,
            @RequestParam("boardContent") String boardContent,
            @RequestParam(value = "boardImage", required = false) MultipartFile boardImage,
            @RequestParam(value = "boardVideo", required = false) MultipartFile boardVideo,
            @RequestParam(value = "boardFile", required = false) MultipartFile boardFile) throws IOException {
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
    public ResponseDto<BoardDetailDto> getBoardWithComments(@PathVariable Integer boardId){
        return boardService.getBoardWithComments(boardId);
    }

    @PostMapping("/{boardId}")
    public void increaseView(@PathVariable Integer boardId, @RequestBody Integer requestBody){
        boardService.increaseView(boardId,requestBody);
    }


    @GetMapping  ("/{boardId}/delete")
    public ResponseDto<String> deleteBoard(@PathVariable Integer boardId){
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

