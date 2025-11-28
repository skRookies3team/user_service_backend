package com.example.petlog.dto.request;

import com.example.petlog.entity.GenderType;
import com.example.petlog.entity.Species;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public class PetRequest {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CreatePetDto {

        //펫 이름(이름, 품종, 나이, 성별)
        @NotNull
        private String petName;

        //품종
        @NotNull
        private String breed;

        //성별
        @NotNull
        private GenderType genderType;

        //나이
        @NotNull
        private Integer age;


    }
}
