//package com.dooho.board.api.board.liky;
//
//import com.dooho.board.api.ResponseDto;
//import com.dooho.board.api.board.BoardEntity;
//import com.dooho.board.api.board.BoardRepository;
//import com.dooho.board.api.board.liky.dto.LikyDto;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Transactional
//@Service
//public class LikyService {
//
//    private final LikyRepository likyRepository;
//    private final BoardRepository boardRepository;
//
//    public LikyService(LikyRepository likyRepository, BoardRepository boardRepository) {
//        this.likyRepository = likyRepository;
//        this.boardRepository = boardRepository;
//    }
//
//    public ResponseDto<?> addLiky(LikyDto dto){
//        likyRepository.save(dto.toEntity());
//        try{
//
//        }catch (Exception e){
//            e.printStackTrace();
//            return ResponseDto.setFailed("DataBase Error");
//        }
//
//        return ResponseDto.setSuccess("Success",null);
//    }
//
//    @Transactional(readOnly = true)
//    public ResponseDto<Integer> getLikyCount(Integer boardId){
//        BoardEntity boardEntity = boardRepository.findById(boardId).orElse(null);
//        Integer temp = 0;
//        try{
//            temp = likyRepository.countByBoard_Id(boardId);
//        }catch (Exception e){
//            e.printStackTrace();
//            return ResponseDto.setFailed("DataBase Error");
//        }
//
//        return ResponseDto.setSuccess("Success",temp);
//    }
//
//
//    public ResponseDto<List<LikyEntity>> getLiky(Integer boardId){
//        List<LikyEntity> likyEntity =  new ArrayList<>();
//        try{
//            likyEntity = likyRepository.findByBoard_Id(boardId);
//        }catch (Exception e){
//            e.printStackTrace();
//            return ResponseDto.setFailed("DataBase Error");
//        }
//
//        return ResponseDto.setSuccess("Success",likyEntity);
//    }
//    public ResponseDto<?> deleteLiky(Integer boardId, String userEmail) {
//        try{
//            // 데이터베이스에서 사용자의 닉네임과 게시글 번호에 해당하는 좋아요 삭제
//            likyRepository.deleteByBoard_IdAndUser_UserEmail(boardId, userEmail);
//            // 해당 게시글의 좋아요 개수 감소
//        }catch (Exception e){
//            e.printStackTrace();
//            return ResponseDto.setFailed("DataBase Error");
//        }
//
//        return ResponseDto.setSuccess("Success",null);
//    }
//}
