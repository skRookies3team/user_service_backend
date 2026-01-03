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

    @Operation(summary = "알람 읽음 처리", description = "알람을 읽음 처리합니다.")
    @PatchMapping("/{user-notificationId}")
    public ResponseEntity<NotificationResponse.GetNotificationDto> readNotification(@PathVariable("user-notificationId") Long notificationId) {
        return ResponseEntity.ok(notificationService.readNotification(notificationId));
    }

    @Operation(summary = "모든 알람 읽음 처리", description = "모든 알람을 읽음 처리합니다.")
    @PatchMapping("/read-all")
    public ResponseEntity<String> readAllNotification(@RequestHeader("X-USER-ID") Long userId) {
        notificationService.readAllNotification(userId);
        return ResponseEntity.ok("모든 알람을 읽음 처리했습니다.");
    }


}
