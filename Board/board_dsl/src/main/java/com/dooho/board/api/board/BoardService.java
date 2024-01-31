package com.dooho.board.api.board;

import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.board.dto.BoardDto;
import com.dooho.board.api.board.dto.BoardWithCommentDto;
import com.dooho.board.api.board.dto.PatchBoardDto;
import com.dooho.board.api.board.dto.PatchBoardResponseDto;
import com.dooho.board.api.file.FileService;
import com.dooho.board.api.user.UserEntity;
import com.dooho.board.api.user.UserRepository;
import com.dooho.board.api.user.dto.UserDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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


    public ResponseDto<BoardEntity> register(
            String userEmail,
            String boardTitle,
            String boardContent,
            MultipartFile boardImage,
            MultipartFile boardVideo,
            MultipartFile boardFile) {

        if (boardRepository.existsByTitle(boardTitle)) {
            return ResponseDto.setFailed("Same Title already exist!");
        }

        UserEntity user = userRepository.getReferenceById(userEmail);
        BoardDto boardDto = BoardDto.of(boardTitle, boardContent, null, null, null, LocalDate.now(), 0, UserDto.from(user), null);
        BoardEntity board = boardDto.toEntity();
        boardRepository.save(board);
        try {
            fileService.uploadFile(boardImage, boardVideo, boardFile, boardDto);
        } catch (Exception e) {
            return ResponseDto.setFailed("DataBase Error!");

        }

        return ResponseDto.setSuccess("Register Success!", board);
    }


    @Transactional(readOnly = true)
    public ResponseDto<BoardWithCommentDto> getBoardWithComments(Integer boardId) {
        BoardWithCommentDto board = boardRepository.findById(boardId)
                .map(BoardWithCommentDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글 없음"));
        return ResponseDto.setSuccess("Success",board);
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


    public ResponseDto<?> deleteBoard(Integer boardId) {
        try {
            boardRepository.deleteById(boardId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error!");
        }
        return ResponseDto.setSuccess("Success", null);
    }

    public ResponseDto<?> increaseView(Integer boardId, Integer increase) {
        BoardEntity boardEntity = boardRepository.findById(boardId).orElse(null);
        Integer boardClick = boardEntity.getClickCount();
        try {
            boardEntity.setClickCount(boardClick + increase);
            boardRepository.save(boardEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error!");
        }
        return ResponseDto.setSuccess("Success", null);
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
