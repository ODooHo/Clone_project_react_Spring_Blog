package com.dooho.board.api.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.board.BoardEntity;
import com.dooho.board.api.board.dto.BoardDto;
import com.dooho.board.api.user.UserEntity;
import com.dooho.board.api.board.BoardRepository;
import com.dooho.board.api.comment.CommentRepository;
import com.dooho.board.api.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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


    public FileService(UserRepository userRepository, BoardRepository boardRepository, CommentRepository commentRepository, AmazonS3 amazonS3) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.commentRepository = commentRepository;
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
                uploadFileToS3(boardImage, imagePath);
                board.setImage(fileName);
            } else {
                board.setImage(null);
            }

            if (boardVideo != null) {
                String fileName = setFileName(boardVideo, board);
                String videoPath = uploadDir +"video/" + fileName;
                uploadFileToS3(boardVideo, videoPath);
                board.setVideo(fileName);
            } else {
                board.setVideo(null);
            }

            if (boardFile != null) {
                String fileName = setFileName(boardFile, board);
                String filePath = uploadDir +"file/" + fileName;
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
            uploadFileToS3(file, uploadDir + "img/"+fileName);
            user.setUserProfile(fileName);
            userRepository.save(user);
            return ResponseDto.setSuccess("Success", fileName);
    }


    public ResponseDto<String> getProfileImage(String imageName){
        String extension = getExtension("", imageName); // 확장자 추출 로직 그대로 사용
        String fileName = imageName + extension;

        String imageUrl = amazonS3.getUrl(bucketName, uploadDir+"img/"+ fileName).toString();

        return ResponseDto.setSuccess("Success",imageUrl);
    }




    public ResponseDto<String> getImage(String imageName){
        String extension = getExtension("", imageName); // 확장자 추출 로직 그대로 사용
        String fileName = imageName + extension;

        String imageUrl = amazonS3.getUrl(bucketName, uploadDir+"img/"+ fileName).toString();

        return ResponseDto.setSuccess("Success",imageUrl);
    }





    public ResponseDto<String> getVideo(String videoName){
        String extension = getExtension("", videoName); // 확장자 추출 로직 그대로 사용
        String fileName = videoName + extension;

        String imageUrl = amazonS3.getUrl(bucketName, uploadDir+"video/"+ fileName).toString();

        return ResponseDto.setSuccess("Success",imageUrl);
    }

    public ResponseEntity<byte[]> getFile(String fileId) {
        if(fileId.equals("null")){
            return ResponseEntity.ok().body(null);
        }
        try {
            S3Object s3Object = amazonS3.getObject(bucketName, uploadDir + "file/" + fileId);
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


    private void uploadFileToS3(MultipartFile file, String s3Key) throws IOException,AmazonS3Exception{
            InputStream inputStream = file.getInputStream();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            amazonS3.putObject(new PutObjectRequest(bucketName, s3Key, inputStream, metadata));
    }

    private String setFileName(MultipartFile file, BoardEntity board) {
        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        return board.getId() + "." + extension;
    }

}
