package com.example.petlog.dto.request;

import com.example.petlog.entity.*;
import com.example.petlog.exception.BusinessException;
import com.example.petlog.exception.ErrorCode;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class NotificationRequest {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CreateNotificationDto {
        //알람 타입
        @NotNull
        private AlarmType type;

        //팔로워/댓글단 사람/좋아요한/매칭보낸 사람 id
        private Long senderId;

        @NotNull
        private List<Long> users;



        public static Notification toEntity(User user, NotificationRequest.CreateNotificationDto request) {

            String content = null;
            String title = null;
            String name = user.getUsername();
            if (request.getType() == AlarmType.LIKE) {
                content = name + "님이 회원님의 게시물을 좋아합니다.";
                title = "좋아요";
            } else if(request.getType() == AlarmType.FOLLOW) {
                content = name + "님이 회원님을 팔로우하기 시작했습니다.";
                title = "새로운 팔로워";
            } else if(request.getType() == AlarmType.COMMENT) {
                content = name + "님이 회원님의 게시물에 댓글을 남겼습니다.";
                title = "새 댓글";
            } else if (request.getType() == AlarmType.MATCH) {
                content = name + "님이 회원님과의 매칭을 원합니다.";
                title = "매칭";
            } else {
                throw new BusinessException(ErrorCode.INVALID_ALARM_TYPE);
            }

            return Notification.builder()
                    .type(request.getType())
                    .title(title)
                    .content(content)
                    .build();

        }
    }
}
