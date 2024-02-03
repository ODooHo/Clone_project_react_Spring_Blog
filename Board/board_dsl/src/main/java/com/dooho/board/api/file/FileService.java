package com.dooho.board.api.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.board.BoardEntity;
import com.dooho.board.api.user.UserEntity;
import com.dooho.board.api.board.BoardRepository;
import com.dooho.board.api.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class FileService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
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


    public FileService(UserRepository userRepository, BoardRepository boardRepository, AmazonS3 amazonS3) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.amazonS3 = amazonS3;
    }

    public String uploadFile(
            MultipartFile boardImage,
            MultipartFile boardVideo,
            MultipartFile boardFile,
            BoardEntity board) throws IOException {
        if (boardImage != null) {
            String fileName = setFileName(boardImage, board);
            String imagePath = uploadDir + "img/" + fileName;
            String imageUrl = uploadFileToS3(boardImage, imagePath);
            board.setImage(imageUrl);
        } else {
            board.setImage(null);
        }

        if (boardVideo != null) {
            String fileName = setFileName(boardVideo, board);
            String videoPath = uploadDir + "video/" + fileName;
            String videoUrl= uploadFileToS3(boardVideo, videoPath);
            board.setVideo(videoUrl);
        } else {
            board.setVideo(null);
        }

        if (boardFile != null) {
            String fileName = setFileName(boardFile, board);
            String filePath = uploadDir + "file/" + fileName;
            uploadFileToS3(boardFile, filePath);
            board.setFile(fileName);
        } else {
            board.setFile(null);
        }

        boardRepository.save(board);

        return "Success";
    }


    public ResponseDto<String> setProfile(MultipartFile file, String userEmail) throws IOException {
        UserEntity user = userRepository.findById(userEmail).orElse(null);
        String fileName = user.getUserEmail() + "." + "jpg";
        // S3 버킷에 파일 업로드
        String url = uploadFileToS3(file, uploadDir + "img/" + fileName);
        user.setUserProfile(url);
        userRepository.save(user);
        return ResponseDto.setSuccess("Success", fileName);
    }

    public ResponseEntity<byte[]> getFile(String fileName) throws IOException {
        if (fileName.equals("null")) {
            return ResponseEntity.ok().body(null);
        }
        S3Object s3Object = amazonS3.getObject(bucketName, uploadDir + "file/" + fileName);
        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

        byte[] fileData = IOUtils.toByteArray(objectInputStream);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);

        return ResponseEntity.ok()
                .headers(headers)
                .body(fileData);
    }


    private String getExtension(String fileDirectory, String fileId) {
        File folder = new File(fileDirectory);

        FilenameFilter filter = (dir, name) -> name.startsWith(fileId);

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


    private String uploadFileToS3(MultipartFile file, String s3Key) throws IOException, AmazonS3Exception {
        InputStream inputStream = file.getInputStream();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        String url = amazonS3.getUrl(bucketName, s3Key).toString();
        amazonS3.putObject(new PutObjectRequest(bucketName, s3Key, inputStream, metadata));
        return url;
    }



    private String setFileName(MultipartFile file, BoardEntity board) {
        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        return board.getId() + "." + extension;
    }

}
