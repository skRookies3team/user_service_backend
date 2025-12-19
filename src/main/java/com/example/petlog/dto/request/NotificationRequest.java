package com.example.petlog.dto.request;

import com.example.petlog.entity.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

public class NotificationRequest {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CreateNotificationDto {

        @NotNull
        private String content;
        //품종
        @NotNull
        private List<Long> users;
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

        public static Pet toEntity(User user, PetRequest.CreatePetDto request, Integer age, String profileImage) {
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
}
