package com.dooho.board.api.auth.controller;

import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @GetMapping("/images/{imageName}/profile")
    public ResponseDto<String> getProfileImage(@PathVariable String imageName)throws IOException{
        ResponseDto<String> result = fileService.getProfileImage(imageName);
        return result;
    }


    @GetMapping("/images/{imageName}")
    public ResponseDto<String> getImage(@PathVariable String imageName){
        ResponseDto<String> result = fileService.getImage(imageName);

        return result;
    }



    @GetMapping("/videos/{videoName}")
    public ResponseDto<String> getVideo(@PathVariable String videoName) throws IOException {
        ResponseDto<String> result = fileService.getVideo(videoName);

        return result;
    }



    @GetMapping("/files/{fileName}")
    public ResponseEntity<byte[]> getFile(@PathVariable String fileName) throws IOException {
        ResponseEntity<byte[]> result = fileService.getFile(fileName);
        return result;
    }

}
