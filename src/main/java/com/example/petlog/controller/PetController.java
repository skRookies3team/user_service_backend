package com.example.petlog.controller;
import com.example.petlog.dto.request.PetRequest;
import com.example.petlog.dto.response.PetResponse;
import com.example.petlog.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @Operation(summary = "펫 등록", description = "새로운 펫을 등록합니다.")
    @PostMapping(value = "/create" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PetResponse.CreatePetDto> createPet(@RequestPart(value = "multipartFile",required = false) MultipartFile multipartFile, @RequestHeader("X-USER-ID") Long userId, @Valid @RequestPart("request") PetRequest.CreatePetDto request) {
        return ResponseEntity.ok(petService.createPet(multipartFile, userId, request));
    }

    @Operation(summary = "펫 정보 수정", description = "기존 펫의 정보를 수정합니다.")
    @PatchMapping(value = "/{petId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PetResponse.UpdatePetDto> updatePet(@RequestHeader("X-USER-ID") Long userId, @PathVariable Long petId,@RequestPart(value = "multipartFile",required = false) MultipartFile multipartFile, @Valid @RequestPart PetRequest.UpdatePetDto request) {
        return ResponseEntity.ok(petService.updatePet(userId, multipartFile, petId, request));
    }

    @Operation(summary = "펫 정보 조회", description = "특정 ID의 펫 정보를 조회합니다.")
    @GetMapping("/{petId}")
    public ResponseEntity<PetResponse.GetPetDto> getPet(@PathVariable Long petId) {
        return ResponseEntity.ok(petService.getPet(petId));
    }

    @Operation(summary = "펫 삭제", description = "특정 ID의 펫을 삭제합니다.")
    @DeleteMapping("/{petId}")
    public ResponseEntity<String> deletePet(@PathVariable Long petId) {
        petService.deletePet(petId);
        return ResponseEntity.ok("펫이 삭제되었습니다.");
    }

    @Operation(summary = "펫 상태 변경 (사망)", description = "특정 ID의 펫 상태를 '사망'으로 변경합니다.")
    @PatchMapping("/lost/{petId}")
    public ResponseEntity<String> lostPet(@PathVariable Long petId) {
        petService.lostPet(petId);
        return ResponseEntity.ok("상태 변경을 완료하였습니다.");
    }

}
