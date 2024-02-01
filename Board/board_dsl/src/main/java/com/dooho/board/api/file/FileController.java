package com.dooho.board.api.file;

import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class FileController {
    private final FileService fileService;


    @PostMapping("/upload/profile")
    public ResponseDto<String> setProfile(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal String userEmail) throws IOException {
        return fileService.setProfile(file, userEmail);
    }

    @GetMapping("/images/{imageName}/profile")
    public ResponseDto<String> getProfileImage(@PathVariable String imageName){
        return fileService.getProfileImage(imageName);
    }


    @GetMapping("/images/{imageName}")
    public ResponseDto<String> getImage(@PathVariable String imageName){
        return fileService.getImage(imageName);
    }



    @GetMapping("/videos/{videoName}")
    public ResponseDto<String> getVideo(@PathVariable String videoName) {
        return fileService.getVideo(videoName);
    }



    @GetMapping("/files/{fileName}")
    public ResponseEntity<byte[]> getFile(@PathVariable String fileName) throws IOException {
        return fileService.getFile(fileName);
    }

}
