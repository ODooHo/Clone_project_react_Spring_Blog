package com.dooho.board.api.board;

import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.file.FileService;
import com.dooho.board.api.user.UserEntity;
import com.dooho.board.api.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Autowired
    public BoardService(BoardRepository boardRepository, FileService fileService,
                        UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.fileService = fileService;
        this.userRepository = userRepository;
    }


    @Transactional(readOnly = true)
    public ResponseDto<BoardEntity> getBoard(Integer boardId) {
        BoardEntity board = null;

        try {
            board = boardRepository.findById(boardId).orElse(null);
        } catch (Exception e) {
            return ResponseDto.setFailed("DataBase Error!");
        }

        return ResponseDto.setSuccess("Success", board);
    }

    public ResponseDto<BoardEntity> register(
            String userEmail,
            String boardTitle,
            String boardContent,
            String boardWriteDate,
            MultipartFile boardImage,
            MultipartFile boardVideo,
            MultipartFile boardFile) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(boardWriteDate, formatter.withZone(ZoneId.of("UTC")));

        LocalDate localDate = zonedDateTime.toLocalDate();

        if (boardRepository.existsByTitle(boardTitle)) {
            return ResponseDto.setFailed("Same Title already exist!");
        }

        UserEntity user = userRepository.getReferenceById(userEmail);
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setTitle(boardTitle);
        boardEntity.setContent(boardContent);
        boardEntity.setBoardWriteDate(localDate);
        boardEntity.setUser(user);
        boardRepository.save(boardEntity);

        try {
            fileService.uploadFile(boardImage, boardVideo, boardFile, boardEntity);
            boardRepository.save(boardEntity);
        } catch (Exception e) {
            return ResponseDto.setFailed("DataBase Error!");

        }

        return ResponseDto.setSuccess("Register Success!", boardEntity);
    }

    @Transactional(readOnly = true)
    public ResponseDto<List<BoardEntity>> getTop3() {
        List<BoardEntity> boardList = new ArrayList<BoardEntity>();
        LocalDate date = LocalDate.now().minusDays(365);
        try {
            boardList = boardRepository.findTop3(date);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error");
        }

        return ResponseDto.setSuccess("Success", boardList);
    }

    public ResponseDto<List<BoardEntity>> getList() {
        List<BoardEntity> boardList = new ArrayList<>();

        try {
            boardList = boardRepository.findList();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error");
        }

        return ResponseDto.setSuccess("Success", boardList);
    }


    public ResponseDto<?> deleteBoard(Integer boardId) {

        try {
            System.out.println("boardId = " + boardId);
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

    public ResponseDto<PatchBoardResponseDto> editBoard(Integer boardId, PatchBoardDto dto) {
        BoardEntity board = null;
        String boardTitle = dto.getBoardTitle();
        String boardContent = dto.getBoardContent();
        LocalDate boardWriteDate = dto.getBoardWriteDate();
        try {
            board = boardRepository.findById(boardId).orElse(null);
            board.setTitle(boardTitle);
            board.setContent(boardContent);
            board.setBoardWriteDate(boardWriteDate);

            boardRepository.save(board);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error!");
        }

        PatchBoardResponseDto patchBoardResponseDto = new PatchBoardResponseDto(board);

        return ResponseDto.setSuccess("Success!", patchBoardResponseDto);
    }
}
