package com.dooho.board.controller;

import com.dooho.board.dto.ResponseDto;
import com.dooho.board.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class FileController {
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload/profile")
    public ResponseDto<String> setProfile(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal String userEmail){
        ResponseDto<String> result = fileService.setProfile(file, userEmail);
        return result;
    }

    @GetMapping("/images/{imageName}")
    public byte[] getImage(@PathVariable String imageName)throws IOException{
        Path imagePath = Paths.get("src/main/resources/static/img/" + imageName);
        return Files.readAllBytes(imagePath);

    }
    @GetMapping("/videos/{videoName}")
    public ResponseEntity<Resource> getVideo(@PathVariable String videoName) throws IOException {
        Path videoPath = Paths.get("src/main/resources/static/video/" + videoName);
        Resource videoResource = new UrlResource(videoPath.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("video/mp4")) // 비디오 타입에 맞게 설정
                .body(videoResource);
    }

    @GetMapping("/files/{fileName}")
    public ResponseEntity<byte[]> getFile(@PathVariable String fileName) throws IOException {
        Path filePath = Paths.get("src/main/resources/static/file/" + fileName);
        byte[] fileData = Files.readAllBytes(filePath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentLength(fileData.length);

        return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
    }

}
