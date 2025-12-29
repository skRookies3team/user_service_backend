package com.example.petlog.service.impl;

import com.example.petlog.dto.request.NotificationRequest;
import com.example.petlog.dto.response.NotificationResponse;
import com.example.petlog.entity.AlarmType;
import com.example.petlog.entity.Notification;
import com.example.petlog.entity.User;
import com.example.petlog.entity.UserNotification;
import com.example.petlog.exception.BusinessException;
import com.example.petlog.exception.ErrorCode;
import com.example.petlog.repository.NotificationRepository;
import com.example.petlog.repository.UserNotificationRepository;
import com.example.petlog.repository.UserRepository;
import com.example.petlog.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final UserNotificationRepository userNotificationRepository;

    @Transactional
    @Override
    public NotificationResponse.CreateNotificationDto createNotification(NotificationRequest.CreateNotificationDto request) {
        User user = userRepository.findById(request.getSenderId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Notification notification =  NotificationRequest.CreateNotificationDto.toEntity(user, request);
        List<User> users = userRepository.findAllById(request.getUsers());
        LocalDateTime time = LocalDateTime.now();
        List<UserNotification> userNotifications = users.stream()
                .map( receiver -> {
                    UserNotification un = UserNotification.builder()
                                    .isRead(false)
                                    .notification(notification)
                                    .sender(user)
                                    .receiver(receiver)
                                    .createdAt(time)
                                    .build();
                    notification.getUserNotifications().add(un);
                    return un;
                }).toList();
        notificationRepository.save(notification);
        return NotificationResponse.CreateNotificationDto.fromEntity(notification, time, false, request.getUsers(), request.getSenderId());



    }

    @Override
    public NotificationResponse.GetNotificationListDto getAllNotifications(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        List<UserNotification> userNotifications = userNotificationRepository.findAllByReceiver(user);
        return NotificationResponse.GetNotificationListDto.fromEntity(userNotifications);
    }
}
