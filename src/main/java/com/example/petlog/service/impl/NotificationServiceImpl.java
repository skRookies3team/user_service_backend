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
        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        LocalDateTime time = LocalDateTime.now();
        UserNotification un = UserNotification.builder()
                .isRead(false)
                .notification(notification)
                .sender(user)
                .receiver(receiver)
                .targetId(request.getTargetId())
                .createdAt(time)
                .build();
        notification.getUserNotifications().add(un);
        notificationRepository.save(notification);
        return NotificationResponse.CreateNotificationDto.fromEntity(notification, time, false, request.getReceiverId(), request.getSenderId());



    }

    @Override
    public NotificationResponse.GetNotificationListDto getAllNotifications(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        List<UserNotification> userNotifications = userNotificationRepository.findAllByReceiverOrderByCreatedAtDesc(user);

        return NotificationResponse.GetNotificationListDto.fromEntity(userNotifications);
    }

    @Override
    public NotificationResponse.GetNotificationDto readNotification(Long notificationId) {
        UserNotification userNotification = userNotificationRepository.findById(notificationId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOTIFICATION_NOT_FOUND));
        userNotification.readNotification();
        userNotificationRepository.save(userNotification);
        return NotificationResponse.GetNotificationDto.fromEntity(userNotification);



    }
}
