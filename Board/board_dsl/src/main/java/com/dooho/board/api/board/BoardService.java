package com.dooho.board.api.board;

import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final FileService fileService;


    @Autowired
    public BoardService(BoardRepository boardRepository, FileService fileService) {
        this.boardRepository = boardRepository;
        this.fileService = fileService;
    }


    public ResponseDto<BoardEntity> getBoard(Integer boardNumber){
        BoardEntity board = null;

        try{
            board = boardRepository.findByBoardNumber(boardNumber);
        }catch (Exception e){
            return ResponseDto.setFailed("DataBase Error!");
        }

        return ResponseDto.setSuccess("Success",board);
    }

    public ResponseDto<BoardEntity> register(
            String boardTitle,
            String boardContent,
            String boardWriterEmail,
            String boardWriterProfile,
            String boardWriterNickname,
            String boardWriteDate,
            MultipartFile boardImage,
            MultipartFile boardVideo,
            MultipartFile boardFile){        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(boardWriteDate, formatter.withZone(ZoneId.of("UTC")));

        LocalDate localDate =  zonedDateTime.toLocalDate();

        if(boardRepository.existsByBoardTitle(boardTitle)){
            return ResponseDto.setFailed("Same Title already exist!");
        }

        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardTitle(boardTitle);
        boardEntity.setBoardContent(boardContent);
        boardEntity.setBoardWriterEmail(boardWriterEmail);
        boardEntity.setBoardWriterProfile(boardWriterProfile);
        boardEntity.setBoardWriterNickname(boardWriterNickname);
        boardEntity.setBoardWriteDate(localDate);
        boardRepository.save(boardEntity);


        try{
            fileService.uploadFile(boardImage,boardVideo,boardFile,boardEntity);
            boardRepository.save(boardEntity);
        }catch (Exception e){
            return ResponseDto.setFailed("DataBase Error!");

        }

        return ResponseDto.setSuccess("Register Success!",boardEntity);
    }

    public ResponseDto<List<BoardEntity>> getTop3(){
        List<BoardEntity> boardList = new ArrayList<BoardEntity>();
        LocalDate date = LocalDate.now().minusDays(365);
        try{
            boardList = boardRepository.findTop3(date);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error");
        }

        return ResponseDto.setSuccess("Success",boardList);
    }

    public ResponseDto<List<BoardEntity>> getList(){
        List<BoardEntity> boardList = new ArrayList<>();

        try{
            boardList = boardRepository.findList();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error");
        }

        return ResponseDto.setSuccess("Success", boardList);
    }


    public ResponseDto<?> deleteBoard(Integer boardNumber) {

        try{
            System.out.println("boardNumber = " + boardNumber);
            boardRepository.deleteBoardEntityByBoardNumber(boardNumber);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error!");
        }
        return ResponseDto.setSuccess("Success",null);
    }

    public ResponseDto<?> increaseView(Integer boardNumber, Integer increase) {
        BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
        Integer boardClick = boardEntity.getBoardClickCount();
        try{
            boardEntity.setBoardClickCount(boardClick + increase);
            boardRepository.save(boardEntity);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error!");
        }
        return ResponseDto.setSuccess("Success",null);
    }

    public ResponseDto<PatchBoardResponseDto> editBoard(Integer boardNumber, PatchBoardDto dto) {
        BoardEntity board = null;
        String boardTitle = dto.getBoardTitle();
        String boardContent = dto.getBoardContent();
        LocalDate boardWriteDate = dto.getBoardWriteDate();
        try{
            board = boardRepository.findByBoardNumber(boardNumber);
            board.setBoardTitle(boardTitle);
            board.setBoardContent(boardContent);
            board.setBoardWriteDate(boardWriteDate);

            boardRepository.save(board);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error!");
        }

        PatchBoardResponseDto patchBoardResponseDto = new PatchBoardResponseDto(board);

        return ResponseDto.setSuccess("Success!",patchBoardResponseDto);
    }
}
