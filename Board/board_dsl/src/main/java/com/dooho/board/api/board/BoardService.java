package com.dooho.board.api.board;

import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.board.dto.BoardDetailDto;
import com.dooho.board.api.board.dto.BoardDto;
import com.dooho.board.api.board.dto.PatchBoardDto;
import com.dooho.board.api.file.FileService;
import com.dooho.board.api.user.UserEntity;
import com.dooho.board.api.user.UserRepository;
import com.dooho.board.api.user.dto.UserDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final FileService fileService;
    private final UserRepository userRepository;


    public BoardService(BoardRepository boardRepository, FileService fileService,
                        UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.fileService = fileService;
        this.userRepository = userRepository;
    }


    public ResponseDto<String> register(
            String userEmail,
            String boardTitle,
            String boardContent,
            MultipartFile boardImage,
            MultipartFile boardVideo,
            MultipartFile boardFile) throws IOException {

        if (boardRepository.existsByTitle(boardTitle)) {
            throw new IllegalArgumentException("BoardTitle Already Exists");
        }

        UserEntity user = userRepository.getReferenceById(userEmail);
        BoardDto boardDto = BoardDto.of(boardTitle, boardContent, null, null, null, LocalDate.now(), 0, 0, 0, UserDto.from(user));
        BoardEntity board = boardDto.toEntity();
        boardRepository.save(board);
        String message = fileService.uploadFile(boardImage, boardVideo, boardFile, board);

        if (message.equals("Failed")) {
            throw new RuntimeException("Error Occurred in file save");
        }

        return ResponseDto.setSuccess("Success", "Success");
    }


    @Transactional(readOnly = true)
    public ResponseDto<BoardDetailDto> getBoardWithComments(Integer boardId) {
        BoardDetailDto board = boardRepository.findById(boardId)
                .map(BoardDetailDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글 없음"));
        return ResponseDto.setSuccess("Success", board);
    }


    @Transactional(readOnly = true)
    public ResponseDto<List<BoardDto>> getTop3() {
        LocalDate date = LocalDate.now().minusDays(365);
        List<BoardDto> boardList = boardRepository.findTop3(date)
                .stream()
                .map(BoardDto::from)
                .toList();

        return ResponseDto.setSuccess("Success", boardList);
    }

    @Transactional(readOnly = true)
    public ResponseDto<List<BoardDto>> getList() {
        List<BoardDto> boardList = boardRepository.findList()
                .stream()
                .map(BoardDto::from)
                .toList();
        return ResponseDto.setSuccess("Success", boardList);
    }


    public ResponseDto<String> deleteBoard(Integer boardId) {
        boardRepository.deleteById(boardId);
        return ResponseDto.setSuccess("Success", "Delete Board Success");
    }

    public void increaseView(Integer boardId, Integer increase) {
        BoardEntity boardEntity = boardRepository.findById(boardId).orElse(null);
        Integer boardClick = boardEntity.getClickCount();
        boardEntity.setClickCount(boardClick + increase);
        boardRepository.save(boardEntity);
    }

    public ResponseDto<BoardDto> editBoard(Integer boardId, PatchBoardDto dto) {
        String boardTitle = dto.boardTitle();
        String boardContent = dto.boardContent();
        BoardEntity board = boardRepository.getReferenceById(boardId);
        board.setTitle(boardTitle);
        board.setContent(boardContent);
        boardRepository.save(board);
        BoardDto response = BoardDto.from(board);
        return ResponseDto.setSuccess("Success!", response);
    }
}
