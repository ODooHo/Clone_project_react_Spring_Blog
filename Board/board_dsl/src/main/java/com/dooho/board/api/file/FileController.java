package com.dooho.board.api.file;

import com.dooho.board.api.ResponseDto;
import lombok.RequiredArgsConstructor;
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
    public ResponseDto<Void> setProfile(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal String userEmail) throws IOException {
        fileService.setProfile(file, userEmail);
        return ResponseDto.setSuccess();
    }

}
