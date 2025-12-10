package com.example.petlog.controller;

import com.example.petlog.dto.request.ImageRequest;
import com.example.petlog.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @Operation(summary = "이미지 업로드", description = "S3 버킷에 이미지를 업로드하고 URL 목록을 반환합니다.")
    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<String>> s3Upload(@RequestPart(value = "multipartFile") List<MultipartFile> multipartFile) {
        List<String> upload = imageService.upload(multipartFile);
        return ResponseEntity.ok(upload);
    }

    @Operation(summary = "특정 사진 삭제", description = "S3에서 사진을 삭제합니다.")
    @DeleteMapping
    public ResponseEntity<String> s3Delete(@RequestBody ImageRequest.DeleteImageDto request) {
        imageService.delete(request.getImageUrls());
        return ResponseEntity.ok("이미지 삭제 성공");
    }
}
