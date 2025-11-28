package com.example.petlog.dto.response;

import com.example.petlog.entity.*;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

public class PetResponse {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class CreatePetDto {
        //펫 id
        private Long petId;

        //펫 이름
        private String petName;

        //생성일
        private LocalDateTime createdAt;

        public static PetResponse.CreatePetDto fromEntity(Pet pet) {

            return PetResponse.CreatePetDto.builder()
                    .petId(pet.getId())
                    .petName(pet.getPetName())
                    .createdAt(pet.getCreatedAt())
                    .build();
        }

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class GetPetDto {
        //펫 이름
        private String petName;
        //종류
        private Species species;
        //품종
        private String breed;
        //성별
        private GenderType genderType;
        //중성화여부
        private boolean is_neutered;
        //프로필 사진
        private String profileImage;
        //나이
        private Integer age;
        //생일
        private LocalDateTime birth;
        //상태
        private Status status;


        public static PetResponse.GetPetDto fromEntity(Pet pet) {

            return PetResponse.GetPetDto.builder()
                    .petName(pet.getPetName())
                    .species(pet.getSpecies())
                    .breed(pet.getBreed())
                    .genderType(pet.getGenderType())
                    .is_neutered(pet.is_neutered())
                    .profileImage(pet.getProfileImage())
                    .age(pet.getAge())
                    .birth(pet.getBirth())
                    .status(pet.getStatus())
                    .build();
        }

    }
}
