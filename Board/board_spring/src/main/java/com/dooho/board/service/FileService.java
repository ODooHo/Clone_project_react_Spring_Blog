package com.dooho.board.service;

import com.dooho.board.dto.ResponseDto;
import com.dooho.board.entity.BoardEntity;
import com.dooho.board.entity.CommentEntity;
import com.dooho.board.entity.UserEntity;
import com.dooho.board.repository.BoardRepository;
import com.dooho.board.repository.CommentRepository;
import com.dooho.board.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.stream.events.Comment;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    public FileService(UserRepository userRepository, BoardRepository boardRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.commentRepository = commentRepository;
    }

    public ResponseDto<String> uploadFile(
            MultipartFile boardImage,
            MultipartFile boardVideo,
            MultipartFile boardFile,
            BoardEntity board){
        try {
            if (boardImage != null) {
                String fileName = setFileName(boardImage,board);
                String imagePath = uploadDir + File.separator + "img" + File.separator + fileName;
                uploadFile(boardImage, imagePath);
                board.setBoardImage(fileName);
            }else{
                board.setBoardImage(null);
            }

            if (boardVideo != null) {
                String fileName = setFileName(boardVideo, board);
                String videoPath = uploadDir + File.separator + "video" + File.separator + fileName;
                uploadFile(boardVideo, videoPath);
                board.setBoardVideo(videoPath);
            }else{
                board.setBoardVideo(null);
            }

            if (boardFile != null) {
                String fileName = setFileName(boardFile, board);
                String filePath = uploadDir + File.separator + "file" + File.separator + fileName;
                uploadFile(boardFile, filePath);
                board.setBoardFile(filePath);
            }else{
                board.setBoardFile(null);
            }

            boardRepository.save(board);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error!");
        }

        return ResponseDto.setSuccess("Success",null);

    }


    public ResponseDto<String> setProfile(MultipartFile file, String userEmail){
        UserEntity user;
        user = userRepository.findByUserEmail(userEmail);
        List<CommentEntity> commentEntity = new ArrayList<>();
        List<BoardEntity> boardEntity = new ArrayList<>();

        commentEntity = commentRepository.findByUserEmail(userEmail);
        boardEntity = boardRepository.findByBoardWriterEmail(userEmail);

        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        String fileName = user.getUserEmail() + "." + "jpg";

        uploadDir = uploadDir + "/img";
        String filePath = uploadDir + File.separator + fileName;

        try{
            uploadFile(file,filePath);
            user.setUserProfile(fileName);
            userRepository.save(user);


            for (CommentEntity comment : commentEntity){
                comment.setCommentUserProfile(fileName);
                commentRepository.save(comment);
            }

            for (BoardEntity board : boardEntity){
                board.setBoardWriterProfile(fileName);
                boardRepository.save(board);
            }

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error");
        }
        return ResponseDto.setSuccess("Success",fileName);
    }


    private void uploadFile(MultipartFile file, String filePath) {
        try {
            File dest = new File(filePath);
            if (dest.exists()) {
                dest.delete(); // 기존 파일 삭제
            }
            file.transferTo(dest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String setFileName(MultipartFile file, BoardEntity board) {
        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        String fileName = board.getBoardNumber() + "." + extension;
        return fileName;
    }

}
