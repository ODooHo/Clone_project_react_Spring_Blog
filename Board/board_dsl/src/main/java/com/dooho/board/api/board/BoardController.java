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
    public ResponseDto<Void> register(
            @AuthenticationPrincipal String userEmail,
            @RequestParam("boardTitle") String boardTitle,
            @RequestParam("boardContent") String boardContent,
            @RequestParam(value = "boardImage", required = false) MultipartFile boardImage,
            @RequestParam(value = "boardVideo", required = false) MultipartFile boardVideo,
            @RequestParam(value = "boardFile", required = false) MultipartFile boardFile) throws IOException {
        boardService.register(userEmail,
                boardTitle, boardContent,
                boardImage,boardVideo,boardFile);

        return ResponseDto.setSuccess();
    }

    @GetMapping("/top3")
    public ResponseDto<List<BoardDto>> getTop3(){
        List<BoardDto> response = boardService.getTop3();
        return ResponseDto.setSuccess(response);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/list")
    public ResponseDto<List<BoardDto>> getList(){
        List<BoardDto> response = boardService.getList();
        return ResponseDto.setSuccess(response);
    }


    @CrossOrigin(origins = "*")
    @GetMapping("/{boardId}")
    public ResponseDto<BoardDetailDto> getBoardWithComments(@PathVariable Integer boardId){
        BoardDetailDto response = boardService.getBoardDetail(boardId);
        return ResponseDto.setSuccess(response);
    }

    @PostMapping("/{boardId}")
    public void increaseView(@PathVariable Integer boardId){
        boardService.increaseView(boardId);
    }


    @GetMapping  ("/{boardId}/delete")
    public ResponseDto<Void> deleteBoard(@PathVariable Integer boardId){
        boardService.deleteBoard(boardId);
        return ResponseDto.setSuccess();
    }

    @CrossOrigin(origins = "*")
    @PatchMapping("{boardId}/edit")
    public ResponseDto<BoardDto> editBoard(
            @PathVariable Integer boardId,
            @RequestBody PatchBoardDto requestBody){
        BoardDto response = boardService.editBoard(boardId, requestBody);
        return ResponseDto.setSuccess(response);
    }
}

