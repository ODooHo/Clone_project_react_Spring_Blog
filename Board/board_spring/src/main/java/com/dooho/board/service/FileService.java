package com.dooho.board.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.dooho.board.dto.ResponseDto;
import com.dooho.board.entity.BoardEntity;
import com.dooho.board.entity.CommentEntity;
import com.dooho.board.entity.UserEntity;
import com.dooho.board.repository.BoardRepository;
import com.dooho.board.repository.CommentRepository;
import com.dooho.board.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
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
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FileService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${default.image.extension}")
    private String defaultImageExtension;

    @Value("${default.video.extension}")
    private String defaultVideoExtension;

    @Value("${default.file.extension}")
    private String defaultFileExtension;


    @Autowired
    public FileService(UserRepository userRepository, BoardRepository boardRepository, CommentRepository commentRepository, AmazonS3 amazonS3) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.commentRepository = commentRepository;
        this.amazonS3 = amazonS3;
    }

    public ResponseDto<String> uploadFile(
            MultipartFile boardImage,
            MultipartFile boardVideo,
            MultipartFile boardFile,
            BoardEntity board) {
        try {
            if (boardImage != null) {
                String fileName = setFileName(boardImage, board);
                String imagePath = "img/" + fileName;
                uploadFileToS3(boardImage, imagePath);
                board.setBoardImage(fileName);
            } else {
                board.setBoardImage(null);
            }

            if (boardVideo != null) {
                String fileName = setFileName(boardVideo, board);
                String videoPath = "video/" + fileName;
                uploadFileToS3(boardVideo, videoPath);
                board.setBoardVideo(fileName);
            } else {
                board.setBoardVideo(null);
            }

            if (boardFile != null) {
                String fileName = setFileName(boardFile, board);
                String filePath = "file/" + fileName;
                uploadFileToS3(boardFile, filePath);
                board.setBoardFile(fileName);
            } else {
                board.setBoardFile(null);
            }

            boardRepository.save(board);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error!");
        }

        return ResponseDto.setSuccess("Success", null);

    }


    public ResponseDto<String> setProfile(MultipartFile file, String userEmail) {
        UserEntity user = userRepository.findByUserEmail(userEmail);

        List<CommentEntity> commentEntity = commentRepository.findByUserEmail(userEmail);
        List<BoardEntity> boardEntity = boardRepository.findByBoardWriterEmail(userEmail);

        String fileName = "img/" + user.getUserEmail() + "." + "jpg";
        try {
            // S3 버킷에 파일 업로드
            uploadFileToS3(file, fileName);

            user.setUserProfile(fileName);
            userRepository.save(user);

            for (CommentEntity comment : commentEntity) {
                comment.setCommentUserProfile(fileName);
                commentRepository.save(comment);
            }

            for (BoardEntity board : boardEntity) {
                board.setBoardWriterProfile(fileName);
                boardRepository.save(board);
            }

            return ResponseDto.setSuccess("Success", fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("Database or S3 Error");
        }
    }


    public ResponseEntity<byte[]> getProfileImage(String imageName) throws IOException {
        try {
            String extension = getExtension("", imageName); // 확장자 추출 로직 그대로 사용
            String fileName = imageName + extension;

            S3Object s3Object = amazonS3.getObject(bucketName, "img/" + fileName);
            S3ObjectInputStream objectInputStream = s3Object.getObjectContent();
            byte[] imageData = IOUtils.toByteArray(objectInputStream);

            HttpHeaders headers = new HttpHeaders();
            MediaType mediaType = MediaType.IMAGE_JPEG;

            if (extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {
                mediaType = MediaType.IMAGE_JPEG;
            } else if (extension.equalsIgnoreCase(".png")) {
                mediaType = MediaType.IMAGE_PNG;
            }

            headers.setContentType(mediaType);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(imageData);
        } catch (Exception e) {
            // Amazon S3에서 이미지를 가져오지 못한 경우 기본 이미지를 반환합니다.
            String defaultImageName = "default-image";
            String defaultExtension = ".png";
            String defaultFileName = defaultImageName + defaultExtension;

            S3Object defaultS3Object = amazonS3.getObject(bucketName, "img/" + defaultFileName);
            S3ObjectInputStream defaultInputStream = defaultS3Object.getObjectContent();
            byte[] defaultImageData = IOUtils.toByteArray(defaultInputStream);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(defaultImageData);
        }
    }




    public ResponseEntity<byte[]> getImage(String imageName) throws IOException {

        String extension = getExtension("", imageName); // 확장자 추출 로직을 그대로 사용
        String fileName = imageName + extension;

        try {
            S3Object s3Object = amazonS3.getObject(bucketName, "img/" + fileName);
            S3ObjectInputStream objectInputStream = s3Object.getObjectContent();
            byte[] imageData = objectInputStream.readAllBytes();

            HttpHeaders headers = new HttpHeaders();
            MediaType mediaType = MediaType.IMAGE_JPEG;

            if (extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {
                mediaType = MediaType.IMAGE_JPEG;
            } else if (extension.equalsIgnoreCase(".png")) {
                mediaType = MediaType.IMAGE_PNG;
            }

            headers.setContentType(mediaType);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(imageData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(null);
        }
    }






    public ResponseEntity<Resource> getVideo(String videoName) throws IOException {
        try {
            S3Object s3Object = amazonS3.getObject(bucketName, "video/" + videoName);
            S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("video/mp4"));

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(new InputStreamResource(objectInputStream));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<byte[]> getFile(String fileId) throws IOException {
        try {
            S3Object s3Object = amazonS3.getObject(bucketName, "file/" + fileId);
            S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

            byte[] fileData = IOUtils.toByteArray(objectInputStream);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", fileId);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }



    private String getExtension(String fileDirectory, String fileId) throws IOException {
        File folder = new File(fileDirectory);

        FilenameFilter filter = (dir, name) -> {
            try{
                return name.startsWith(fileId);
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }
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


    private void uploadFileToS3(MultipartFile file, String s3Key) {
        try {
            InputStream inputStream = file.getInputStream();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            amazonS3.putObject(new PutObjectRequest(bucketName, s3Key, inputStream, metadata));
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
