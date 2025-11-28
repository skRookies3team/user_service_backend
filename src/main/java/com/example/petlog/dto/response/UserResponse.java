package com.example.petlog.dto.response;

import com.example.petlog.entity.GenderType;
import com.example.petlog.entity.UserType;
import com.example.petlog.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

public class UserResponse {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateUserDto {

        private String email;

        private String username;

        private Long userId;

        private LocalDateTime createdAt;

        public static CreateUserDto fromEntity(User user) {

            return CreateUserDto.builder()
                    .userId(user.getId())
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .createdAt(user.getCreatedAt())
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LoginDto {
        private String token;
        private Long userId;
        private UserType role;
        private String email;
        private String username;

        public static LoginDto fromEntity(String token, UserResponse.AuthDto userDetails) {
            return UserResponse.LoginDto.builder()
                    .token(token)
                    .userId(userDetails.getUserId())
                    .role(userDetails.getType())
                    .email(userDetails.getEmail())
                    .username(userDetails.getUsername())
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AuthDto {
        private Long userId;
        private String email;
        private String username;
        private String password;
        private UserType type;
        private String encryptedPwd;
        private LocalDateTime createdAt;

        public static AuthDto fromEntity(User user) {

            return AuthDto.builder()
                    .userId(user.getId())
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .type(user.getType())
                    .encryptedPwd(user.getEncryptedPwd())
                    .createdAt(user.getCreatedAt())
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class GetUserDto {

        //사용자 이름(닉네임)
        private String username;
        //성별
        private GenderType genderType;
        //프로필 사진
        private String profileImage;
        //나이
        private Integer age;
        //위도
        private Integer currentLat;
        //경도
        private Integer currentLng;
        //펫코인
        private Long petCoin;
        //펫 정보
        private List<PetResponse.GetPetDto> pets;

        public static GetUserDto fromEntity(User user, List<PetResponse.GetPetDto> pets) {

            return GetUserDto.builder()
                    .username(user.getUsername())
                    .genderType(user.getGenderType())
                    .profileImage(user.getProfileImage())
                    .age(user.getAge())
                    .currentLat(user.getCurrentLat())
                    .currentLng(user.getCurrentLng())
                    .petCoin(user.getPetCoin())
                    .pets(pets)
                    .build();
        }
    }



}
