package com.example.petlog.dto.request;


import com.example.petlog.entity.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
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
        //생일
        @NotNull
        private LocalDate birth;
        //종류
        @NotNull
        private Species species;
        //중성화여부
        @NotNull
        private boolean neutered;
        //예방접종 여부
        @NotNull
        private boolean vaccinated;

        public static Pet toEntity(User user, CreatePetDto request, Integer age, String profileImage) {
            return Pet.builder()
                    .user(user)
                    .petName(request.getPetName())
                    .genderType(request.getGenderType())
                    .breed(request.getBreed())
                    .age(age)
                    .status(Status.ALIVE)
                    .birth(request.getBirth())
                    .species(request.getSpecies())
                    .neutered(request.isNeutered())
                    .profileImage(profileImage)
                    .isVaccinated(request.isVaccinated())
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UpdatePetDto {

        //펫 이름
        private String petName;
        //품종
        private String breed;
        //성별
        private GenderType genderType;
        //나이
        private Integer age;
        //생일
        private LocalDate birth;
        //종류
        private Species species;
        //중성화여부
        private boolean neutered;
        //예방접종 여부
        private boolean vaccinated;
    }
}
