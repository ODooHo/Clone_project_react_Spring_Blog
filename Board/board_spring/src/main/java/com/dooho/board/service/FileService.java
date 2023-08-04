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
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
                board.setBoardVideo(fileName);
            }else{
                board.setBoardVideo(null);
            }

            if (boardFile != null) {
                String fileName = setFileName(boardFile, board);
                String filePath = uploadDir + File.separator + "file" + File.separator + fileName;
                uploadFile(boardFile, filePath);
                board.setBoardFile(fileName);
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


    public byte[] getImage(String imageName) throws IOException {
        String fileDirectory = "src/main/resources/static/img/";

        String fileName = imageName + getExtension(fileDirectory, imageName);

        Path imagePath = Paths.get(fileDirectory + fileName);

        return Files.readAllBytes(imagePath);
    }




    public ResponseEntity<Resource> getVideo(String videoName) throws IOException {

        String fileDirectory = "src/main/resources/static/video/";

        String fileName = videoName + getExtension(fileDirectory, videoName);

        Path videoPath = Paths.get(fileDirectory + fileName);
        Resource videoResource = new UrlResource(videoPath.toUri());
        System.out.println("videoResource = " + videoResource);
        String videoUrl = "http://localhost:4000/videos/" + fileName;
        System.out.println("videoUrl = " + videoUrl);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("video/mp4")) // 비디오 타입에 맞게 설정
                .body(videoResource);
    }

    public ResponseEntity<byte[]> getFile(String fileId) throws IOException {
        // 파일이 저장된 디렉토리 경로
        String fileDirectory = "src/main/resources/static/file/";

        // 파일의 확장자를 추출하여 파일 이름을 만듭니다.
        String fileName = fileId + getExtension(fileDirectory, fileId);

        // 원본 파일이 존재하는지 확인합니다.
        Path filePath = Paths.get(fileDirectory + fileName);
        if (!Files.exists(filePath)) {
            // 파일이 존재하지 않으면 클라이언트에게 오류를 응답합니다.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // 모든 파일의 MIME 타입을 "application/octet-stream"으로 설정하여 다운로드 가능하게 합니다.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);

        // 파일 데이터를 읽어옵니다.
        byte[] fileData = Files.readAllBytes(filePath);

        // 파일 데이터와 헤더를 포함한 응답을 반환합니다.
        return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
    }



    private String getExtension(String fileDirectory, String fileId) throws IOException {
        File folder = new File(fileDirectory);

        FilenameFilter filter = (dir, name) -> {
            return name.startsWith(fileId);
        };

        String[] files = folder.list(filter);

        if (files == null || files.length == 0) {
            return "";
        }

        String fileName = files[0];

        // 확장자 추출
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex);
        }
        //파일이 없다면

        return "";
    }

    private String getExtension(String fileDirectory, Integer fileId) throws IOException {
        File folder = new File(fileDirectory);

        FilenameFilter filter = (dir, name) -> {
            return name.startsWith(String.valueOf(fileId));
        };

        String[] files = folder.list(filter);

        if (files == null || files.length == 0) {
            return "";
        }

        String fileName = files[0];

        // 확장자 추출
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex);
        }
        //파일이 없다면

        return "";
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
