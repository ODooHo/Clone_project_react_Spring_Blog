package com.dooho.board.controller;

import com.dooho.board.dto.ResponseDto;
import com.dooho.board.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
    public ResponseEntity<byte[]> getProfileImage(@PathVariable String imageName)throws IOException{
        ResponseEntity<byte[]> result = fileService.getProfileImage(imageName);

        return result;
    }


    @GetMapping("/images/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName)throws IOException{
        ResponseEntity<byte[]> result = fileService.getImage(imageName);

        return result;
    }



    @GetMapping("/videos/{videoName}")
    public ResponseEntity<Resource> getVideo(@PathVariable String videoName) throws IOException {
        ResponseEntity<Resource> result = fileService.getVideo(videoName);

        return result;
    }



    @GetMapping("/files/{fileName}")
    public ResponseEntity<byte[]> getFile(@PathVariable String fileName) throws IOException {
        ResponseEntity<byte[]> result = fileService.getFile(fileName);
        return result;
    }

}
