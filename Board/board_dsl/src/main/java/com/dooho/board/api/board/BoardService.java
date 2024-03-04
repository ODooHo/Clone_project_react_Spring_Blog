package com.dooho.board.api.board;

import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.board.dto.BoardDetailDto;
import com.dooho.board.api.board.dto.BoardDto;
import com.dooho.board.api.board.dto.PatchBoardDto;
import com.dooho.board.api.exception.BoardApplicationException;
import com.dooho.board.api.exception.ErrorCode;
import com.dooho.board.api.file.FileService;
import com.dooho.board.api.user.UserEntity;
import com.dooho.board.api.user.UserRepository;
import com.dooho.board.api.user.dto.UserDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardSearchRepository boardSearchRepository;
    private final FileService fileService;
    private final UserRepository userRepository;

    public void register(
            String userEmail,
            String boardTitle,
            String boardContent,
            MultipartFile boardImage,
            MultipartFile boardVideo,
            MultipartFile boardFile) throws IOException {

        UserEntity user = userRepository.getReferenceById(userEmail);
        BoardDto boardDto = BoardDto.of(boardTitle, boardContent, null, null, null, LocalDate.now(), 0, 0, 0, UserDto.from(user));
        BoardEntity board = boardDto.toEntity();
        boardRepository.save(board);
        String message = fileService.uploadFile(boardImage, boardVideo, boardFile, board);

        if (message.equals("Failed")) {
            throw new RuntimeException("Error Occurred in file save");
        }

    }


    @Transactional(readOnly = true)
    public BoardDetailDto getBoardDetail(Integer boardId) {
        return boardSearchRepository.findById(boardId)
                .map(BoardDetailDto::from)
                .orElseThrow(() -> new BoardApplicationException(ErrorCode.BOARD_NOT_FOUND, String.format("boardId is %d", boardId)));
    }


    @Transactional(readOnly = true)
    public List<BoardDto> getTop3() {
        LocalDate date = LocalDate.now().minusDays(365);
        return boardSearchRepository.findTop3ByLikesCountOrderByBoardWriteDateDesc()
                .stream()
                .map(BoardDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BoardDto> getList() {
        return boardRepository.findList()
                .stream()
                .map(BoardDto::from)
                .toList();
    }


    public void deleteBoard(Integer boardId) {
        boardRepository.deleteById(boardId);
    }

    public void increaseView(Integer boardId) {
        BoardEntity boardEntity = boardRepository.findById(boardId).orElseThrow(
                () -> new BoardApplicationException(ErrorCode.BOARD_NOT_FOUND,String.format("boardId is %d",boardId))
        );
        int boardClick = boardEntity.getClickCount();
        boardEntity.setClickCount(boardClick + 1);
        boardRepository.save(boardEntity);
    }

    public BoardDto editBoard(Integer boardId, PatchBoardDto dto) {
        String boardTitle = dto.boardTitle();
        String boardContent = dto.boardContent();
        BoardEntity board = boardRepository.getReferenceById(boardId);
        board.setTitle(boardTitle);
        board.setContent(boardContent);
        boardRepository.save(board);
        return BoardDto.from(board);
    }
}
