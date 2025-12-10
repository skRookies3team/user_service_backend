package com.example.petlog.controller;

import com.example.petlog.dto.request.ArchiveRequest;
import com.example.petlog.dto.request.ImageRequest;
import com.example.petlog.dto.response.ArchiveResponse;
import com.example.petlog.service.ArchiveService;
import com.example.petlog.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
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

    private final ArchiveService archiveService;


    @Operation(summary = "사진 생성", description = "보관함에 사진을 저장합니다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ArchiveResponse.CreateArchiveDtoList> createArchive(ArchiveRequest.CreateArchiveDto request, @RequestHeader("X-USER-ID") Long userId) {
        return ResponseEntity.ok(archiveService.createArchive(request,userId));
    }

    @Operation(summary = "내 모든 사진 조회", description = "현재 로그인된 사용자의 보관함에 있는 모든 사진을 조회합니다.")
    @GetMapping("/me")
    public ResponseEntity<ArchiveResponse.CreateArchiveDtoList> getAllArchives(@RequestHeader("X-USER-ID") Long userId) {
        return ResponseEntity.ok(archiveService.getAllArchives(userId));
    }

    @Operation(summary = "특정 사진 조회", description = "보관함에서 특정 ID의 사진을 조회합니다.")
    @GetMapping("/{archiveId}")
    public ResponseEntity<ArchiveResponse.CreateArchiveDto> getArchive(@PathVariable("archiveId") Long archiveId) {
        return ResponseEntity.ok(archiveService.getArchive(archiveId));
    }

    @Operation(summary = "보관함에서 사진 삭제", description = "보관함에서 사진을 삭제합니다.")
    @DeleteMapping
    public ResponseEntity<String> s3Delete(@RequestHeader("X-USER-ID") Long userId, @RequestBody ArchiveRequest.DeleteArchiveDto request) {
        archiveService.delete(userId, request);
        return ResponseEntity.ok("이미지 삭제 성공");
    }

}
