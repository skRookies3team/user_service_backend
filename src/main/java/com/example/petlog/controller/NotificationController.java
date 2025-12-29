package com.example.petlog.controller;

import com.example.petlog.dto.request.NotificationRequest;
import com.example.petlog.dto.request.PetRequest;
import com.example.petlog.dto.response.NotificationResponse;
import com.example.petlog.dto.response.PetResponse;
import com.example.petlog.service.NotificationService;
import com.example.petlog.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "알람 생성", description = "알람을 생성합니다.")
    @PostMapping("/create")
    public ResponseEntity<NotificationResponse.CreateNotificationDto> createNotification(@Valid @RequestBody NotificationRequest.CreateNotificationDto request) {
        return ResponseEntity.ok(notificationService.createNotification(request));
    }

    @Operation(summary = "알람 내역 조회", description = "로그인한 사용자의 알람 내역을 조회합니다.")
    @GetMapping
    public ResponseEntity<NotificationResponse.GetNotificationListDto> getAllNotifications(@RequestHeader("X-USER-ID") Long userId) {
        return ResponseEntity.ok(notificationService.getAllNotifications(userId));
    }

    /*@Operation(summary = "펫 정보 수정", description = "기존 펫의 정보를 수정합니다.")
    @PatchMapping(value = "/{petId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PetResponse.UpdatePetDto> updatePet(@RequestHeader("X-USER-ID") Long userId, @PathVariable Long petId,@RequestPart(value = "multipartFile",required = false) MultipartFile multipartFile, @Valid @RequestPart PetRequest.UpdatePetDto request) {
        return ResponseEntity.ok(petService.updatePet(userId, multipartFile, petId, request));
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
    }*/
}
