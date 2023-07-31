package com.dooho.board.service;

import com.dooho.board.dto.ResponseDto;
import com.dooho.board.entity.BoardEntity;
import com.dooho.board.entity.UserEntity;
import com.dooho.board.repository.BoardRepository;
import com.dooho.board.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class FileService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    public FileService(UserRepository userRepository, BoardRepository boardRepository) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
    }

    public ResponseDto<String> uploadFile(
            MultipartFile boardImage,
            MultipartFile boardVideo,
            MultipartFile boardFile,
            BoardEntity board){
        try {
            if (boardImage != null) {
                String imagePath = uploadDir + File.separator + "img" + File.separator + setFileName(boardImage, board);
                uploadFile(boardImage, imagePath);
                board.setBoardImage(imagePath);
            }else{
                board.setBoardImage(null);
            }

            if (boardVideo != null) {
                String videoPath = uploadDir + File.separator + "video" + File.separator + setFileName(boardVideo, board);
                uploadFile(boardVideo, videoPath);
                board.setBoardVideo(videoPath);
            }else{
                board.setBoardVideo(null);
            }

            if (boardFile != null) {
                String filePath = uploadDir + File.separator + "file" + File.separator + setFileName(boardFile, board);
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
        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        String fileName = user.getUserNickname() + "." + extension;

        uploadDir = uploadDir + "/img";
        String filePath = uploadDir + File.separator + fileName;

        try{
            uploadFile(file,filePath);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error");
        }
        return ResponseDto.setSuccess("Success",filePath);
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
        String fileName = board.getBoardTitle() + "." + "jpg";
        return fileName;
    }

}
