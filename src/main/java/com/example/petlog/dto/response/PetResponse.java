package com.example.petlog.dto.response;

import com.example.petlog.entity.*;
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
        //품종
        private String breed;
        //나이
        private Integer age;
        //성별
        private GenderType genderType;
        //생성일
        private LocalDateTime createdAt;

        public static PetResponse.CreatePetDto fromEntity(Pet pet) {

            return CreatePetDto.builder()
                    .petId(pet.getId())
                    .petName(pet.getPetName())
                    .breed(pet.getBreed())
                    .genderType(pet.getGenderType())
                    .createdAt(pet.getCreatedAt())
                    .build();
        }

    }
}
