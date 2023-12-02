package com.dooho.board.api.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.board.BoardEntity;
import com.dooho.board.api.comment.CommentEntity;
import com.dooho.board.api.user.UserEntity;
import com.dooho.board.api.board.BoardRepository;
import com.dooho.board.api.comment.CommentRepository;
import com.dooho.board.api.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
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
                String imagePath = uploadDir + "img/" + fileName;
                uploadFileToS3(boardImage, imagePath);
                board.setBoardImage(fileName);
            } else {
                board.setBoardImage(null);
            }

            if (boardVideo != null) {
                String fileName = setFileName(boardVideo, board);
                String videoPath = uploadDir +"video/" + fileName;
                uploadFileToS3(boardVideo, videoPath);
                board.setBoardVideo(fileName);
            } else {
                board.setBoardVideo(null);
            }

            if (boardFile != null) {
                String fileName = setFileName(boardFile, board);
                String filePath = uploadDir +"file/" + fileName;
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

        String fileName = user.getUserEmail() + "." + "jpg";
        try {
            // S3 버킷에 파일 업로드
            uploadFileToS3(file, uploadDir + "img/"+fileName);

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


    public ResponseDto<String> getProfileImage(String imageName){
        try {
            String extension = getExtension("", imageName); // 확장자 추출 로직 그대로 사용
            String fileName = imageName + extension;

            String imageUrl = amazonS3.getUrl(bucketName, uploadDir+"img/"+ fileName).toString();

            return ResponseDto.setSuccess("Success",imageUrl);
        } catch (AmazonS3Exception e) {
            return ResponseDto.setFailed("Cloud Error!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error");
        }
    }




    public ResponseDto<String> getImage(String imageName){
        try {
            String extension = getExtension("", imageName); // 확장자 추출 로직 그대로 사용
            String fileName = imageName + extension;

            String imageUrl = amazonS3.getUrl(bucketName, uploadDir+"img/"+ fileName).toString();

            return ResponseDto.setSuccess("Success",imageUrl);
        } catch (AmazonS3Exception e) {
            return ResponseDto.setFailed("Cloud Error!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error");
        }
    }





    public ResponseDto<String> getVideo(String videoName){
        try {
            String extension = getExtension("", videoName); // 확장자 추출 로직 그대로 사용
            String fileName = videoName + extension;

            String imageUrl = amazonS3.getUrl(bucketName, uploadDir+"video/"+ fileName).toString();

            return ResponseDto.setSuccess("Success",imageUrl);
        } catch (AmazonS3Exception e) {
            return ResponseDto.setFailed("Cloud Error!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error");
        }
    }

    public ResponseEntity<byte[]> getFile(String fileId) throws IOException {
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
