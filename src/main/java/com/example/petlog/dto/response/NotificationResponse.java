package com.example.petlog.dto.response;


import com.example.petlog.entity.AlarmType;
import com.example.petlog.entity.Archive;
import com.example.petlog.entity.Notification;
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
        //수신자
        private List<Long> users;
        //시간
        private LocalDateTime time;
        //읽음 여부
        private boolean isRead;

        public static CreateNotificationDto fromEntity(Notification notification, LocalDateTime time, boolean isRead, List<Long> users) {

            return CreateNotificationDto.builder()
                    .alarmId(notification.getId())
                    .type(notification.getType())
                    .title(notification.getTitle())
                    .content(notification.getContent())
                    .time(time)
                    .isRead(isRead)
                    .users(users)
                    .build();
        }
    }
}
