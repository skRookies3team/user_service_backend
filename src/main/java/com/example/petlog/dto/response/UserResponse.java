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
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
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
        private String social;

        public static LoginDto fromEntity(String token, UserResponse.AuthDto userDetails) {
            return UserResponse.LoginDto.builder()
                    .token(token)
                    .userId(userDetails.getUserId())
                    .social(userDetails.getSocial())
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
        private String social;
        private UserType type;
        private String encryptedPwd;
        private LocalDateTime createdAt;

        public static AuthDto fromEntity(User user) {

            return AuthDto.builder()
                    .userId(user.getId())
                    .social(user.getSocial())
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
        //소설
        private String social;
        //상태메세지
        private String statusMessage;
        //나이
        private Integer age;
        //생일
        private LocalDate birth;
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
                    .statusMessage(user.getStatusMessage())
                    .username(user.getUsername())
                    .social(user.getSocial())
                    .genderType(user.getGenderType())
                    .profileImage(user.getProfileImage())
                    .birth(user.getBirth())
                    .age(user.getAge())
                    .currentLat(user.getCurrentLat())
                    .currentLng(user.getCurrentLng())
                    .petCoin(user.getPetCoin())
                    .pets(pets)
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateUserDto {
        //사용자 이름(닉네임)
        private String username;
        //성별
        private GenderType genderType;
        //프로필 사진
        private String profileImage;
        //나이
        private Integer age;

        public static UpdateUserDto fromEntity(User user) {

            return UpdateUserDto.builder()
                    .username(user.getUsername())
                    .genderType(user.getGenderType())
                    .profileImage(user.getProfileImage())
                    .age(user.getAge())
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateProfileDto {
        //사용자 이름(닉네임)
        private String username;
        //프로필 사진
        private String profileImage;
        //상태메세지
        private String statusMessage;

        public static UpdateProfileDto fromEntity(User user) {

            return UpdateProfileDto.builder()
                    .username(user.getUsername())
                    .profileImage(user.getProfileImage())
                    .statusMessage(user.getStatusMessage())
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CoinDto {
        private Long petCoin;

        public static CoinDto fromEntity(User user) {
            return CoinDto.builder()
                    .petCoin(user.getPetCoin())
                    .build();
        }

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class GetSearchedUserDto {
        //사용자 id
        Long userId;
        //사용자 이름(닉네임)
        private String username;
        //성별
        private GenderType genderType;
        //프로필 사진
        private String profileImage;
        //소설
        private String social;
        //상태메세지
        private String statusMessage;
        //나이
        private Integer age;

        public static GetSearchedUserDto fromEntity(User user) {

            return GetSearchedUserDto.builder()
                    .userId(user.getId())
                    .statusMessage(user.getStatusMessage())
                    .username(user.getUsername())
                    .social(user.getSocial())
                    .genderType(user.getGenderType())
                    .profileImage(user.getProfileImage())
                    .age(user.getAge())
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class GetSearchedUserDtoList {

        //사용자 이름(닉네임)
        boolean isEmpty;
        private List<GetSearchedUserDto> users;

        public static GetSearchedUserDtoList fromEntity(List<User> users) {
            boolean isEmpty = users.isEmpty();
            List<GetSearchedUserDto> getSearchedUserDtoList = users.stream()
                    .map(user -> GetSearchedUserDto.fromEntity(user))
                    .toList();
            return GetSearchedUserDtoList.builder()
                    .users(getSearchedUserDtoList)
                    .isEmpty(isEmpty)
                    .build();
        }
    }


}
