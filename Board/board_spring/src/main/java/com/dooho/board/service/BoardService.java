package com.dooho.board.service;

import com.dooho.board.dto.BoardDto;
import com.dooho.board.dto.ResponseDto;
import com.dooho.board.entity.BoardEntity;
import com.dooho.board.entity.PopularSearchEntity;
import com.dooho.board.repository.BoardRepository;
import com.dooho.board.repository.PopularSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    private final PopularSearchRepository popularSearchRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository, PopularSearchRepository popularSearchRepository) {
        this.boardRepository = boardRepository;
        this.popularSearchRepository = popularSearchRepository;
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

    public ResponseDto<?> register(BoardDto dto){
        String boardTitle = dto.getBoardTitle();


        BoardEntity boardEntity = new BoardEntity(dto);




        try{
            if(boardRepository.existsByBoardTitle(boardTitle)){
                return ResponseDto.setFailed("Same Title already exist!");
            }
            boardRepository.save(boardEntity);
        }catch (Exception e){
            return ResponseDto.setFailed("DataBase Error!");

        }

        return ResponseDto.setSuccess("Register Success!",null);
    }

    public ResponseDto<List<BoardEntity>> getTop3(){
        List<BoardEntity> boardList = new ArrayList<BoardEntity>();
        LocalDate date = LocalDate.now().minusDays(7);
        try{
            boardList = boardRepository.findTop3ByBoardWriteDateAfterOrderByBoardLikeCountDesc(date);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error");
        }

        return ResponseDto.setSuccess("Success",boardList);
    }

    public ResponseDto<List<BoardEntity>> getList(){
        List<BoardEntity> boardList = new ArrayList<BoardEntity>();

        try{
            boardList = boardRepository.findByOrderByBoardWriteDateDesc();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error");
        }

        return ResponseDto.setSuccess("Success", boardList);
    }

    public ResponseDto<List<PopularSearchEntity>> getPopularsearchList(){
        List<PopularSearchEntity> popularSearchList = new ArrayList<PopularSearchEntity>();

        try{
            popularSearchList = popularSearchRepository.findTop10ByOrderByPopularSearchCountDesc();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error!");
        }

        return ResponseDto.setSuccess("Success",popularSearchList);
    }

    public ResponseDto<List<BoardEntity>> getSearchList(String boardTitle){

        List<BoardEntity> boardList = new ArrayList<BoardEntity>();

        try{
            boardList = boardRepository.findByBoardTitleContains(boardTitle);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error!");
        }

        return ResponseDto.setSuccess("Success",boardList);
    }


}
