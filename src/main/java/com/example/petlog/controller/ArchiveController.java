package com.example.petlog.controller;

import com.example.petlog.service.ImageService;
import com.example.petlog.service.impl.S3ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/archives")
@RequiredArgsConstructor
public class ArchiveController {

    private final ImageService imageService;

    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<String>> s3Upload(@RequestPart(value = "multipartFile") List<MultipartFile> multipartFile) {
        List<String> upload = imageService.upload(multipartFile);
        return ResponseEntity.ok(upload);
    }


}
