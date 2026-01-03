package com.example.petlog.service;

import com.example.petlog.dto.request.NotificationRequest;
import com.example.petlog.dto.response.NotificationResponse;
import com.example.petlog.dto.response.PetResponse;
import jakarta.validation.Valid;

public interface NotificationService {
    NotificationResponse.CreateNotificationDto createNotification(NotificationRequest.CreateNotificationDto request);

    NotificationResponse.GetNotificationListDto getAllNotifications(Long userId);

    NotificationResponse.GetNotificationDto readNotification(Long notificationId);

    void readAllNotification(Long userId);
}
