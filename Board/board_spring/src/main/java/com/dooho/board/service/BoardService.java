package com.dooho.board.service;

import com.dooho.board.dto.BoardDto;
import com.dooho.board.dto.ResponseDto;
import com.dooho.board.dto.SignUpDto;
import com.dooho.board.entity.BoardEntity;
import com.dooho.board.entity.PopularSearchEntity;
import com.dooho.board.entity.UserEntity;
import com.dooho.board.repository.BoardRepository;
import com.dooho.board.repository.PopularSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    PopularSearchRepository popularSearchRepository;


    public ResponseDto<?> register(BoardDto dto){
        List<BoardEntity> boardList = new ArrayList<BoardEntity>();

        // userEntity 생성,
        BoardEntity boardEntity = new BoardEntity(dto);

        boardList = boardRepository.findAll();

        //repository(db)에 저d
        try{
            boardRepository.save(boardEntity);
        }catch (Exception e){
            return ResponseDto.setFailed("DataBase Error!");
        }

        return ResponseDto.setSuccess("Register Success!",boardList);
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
