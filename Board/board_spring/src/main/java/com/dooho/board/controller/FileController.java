package com.dooho.board.controller;

import com.dooho.board.dto.ResponseDto;
import com.dooho.board.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
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

}
