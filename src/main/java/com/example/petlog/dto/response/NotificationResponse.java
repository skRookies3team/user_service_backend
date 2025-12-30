package com.example.petlog.dto.response;


import com.example.petlog.entity.AlarmType;
import com.example.petlog.entity.Archive;
import com.example.petlog.entity.Notification;
import com.example.petlog.entity.UserNotification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class NotificationResponse {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class CreateNotificationDto {

        //알람 id
        private Long alarmId;
        //알람 타입
        private AlarmType type;
        //알람 제목
        private String title;
        //알람 내용
        private String content;
        //발신자
        private Long senderId;
        //수신자
        private List<Long> receivers;
        //시간
        private LocalDateTime time;
        //읽음 여부
        private boolean isRead;

        public static CreateNotificationDto fromEntity(Notification notification, LocalDateTime time, boolean isRead, List<Long> users, Long senderId) {

            return CreateNotificationDto.builder()
                    .alarmId(notification.getId())
                    .type(notification.getType())
                    .title(notification.getTitle())
                    .content(notification.getContent())
                    .time(time)
                    .senderId(senderId)
                    .isRead(isRead)
                    .receivers(users)
                    .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class GetNotificationDto {
        private Long notificationId;
        private Long userNotificationId;
        private Long senderId;
        private Long receiverId;
        private String content;
        private String title;
        private LocalDateTime time;
        private boolean isRead;

        public static GetNotificationDto fromEntity(UserNotification userNotification) {
            return GetNotificationDto.builder()
                    .content(userNotification.getNotification().getContent())
                    .time(userNotification.getCreatedAt())
                    .title(userNotification.getNotification().getTitle())
                    .isRead(userNotification.isRead())
                    .notificationId(userNotification.getNotification().getId())
                    .userNotificationId(userNotification.getId())
                    .senderId(userNotification.getSender().getId())
                    .receiverId(userNotification.getReceiver().getId())
                    .build();
        }

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class GetNotificationListDto {
        boolean isEmpty;
        private List<GetNotificationDto> notifications;

        public static GetNotificationListDto fromEntity(List<UserNotification> userNotifications) {
            boolean isEmpty = userNotifications.isEmpty();
            List<GetNotificationDto> notifications = userNotifications.stream()
                    .map(userNotification ->GetNotificationDto.fromEntity(userNotification))
                    .toList();
            return GetNotificationListDto.builder()
                    .notifications(notifications)
                    .isEmpty(isEmpty)
                    .build();

        }
    }
}
