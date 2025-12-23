package com.example.petlog.dto.response;

import com.example.petlog.entity.*;
import lombok.*;

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
        //소설아이디
        private String social;

        public static UpdateProfileDto fromEntity(User user) {

            return UpdateProfileDto.builder()
                    .username(user.getUsername())
                    .profileImage(user.getProfileImage())
                    .social(user.getSocial())
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CoinDto {
        //사용자 id
        private Long userId;
        //보유량
        private Long petCoin;
        public static CoinDto fromEntity(User user) {
            return CoinDto.builder()
                    .userId(user.getId())
                    .petCoin(user.getPetCoin())
                    .build();
        }

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CoinLogDto {
        //사용자 id
        private Long userId;
        //보유량
        private Long petCoin;
        //변화량
        private Long amount;
        //타입
        private CoinType type;
        //적립일
        private LocalDateTime time;
        public static CoinLogDto fromEntity(User user, Long amount, CoinType type, LocalDateTime time) {
            return CoinLogDto.builder()
                    .amount(amount)
                    .type(type)
                    .userId(user.getId())
                    .petCoin(user.getPetCoin())
                    .time(time)
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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AnalyzeAnimalDto {
        //종
        private Species species;
        //보유량
        private String breed;
        public static AnalyzeAnimalDto fromEntity(Species species, String breed) {
            return AnalyzeAnimalDto.builder()
                    .species(species)
                    .breed(breed)
                    .build();
        }

    }


}
