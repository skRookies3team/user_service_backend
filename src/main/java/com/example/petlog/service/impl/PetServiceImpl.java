package com.example.petlog.service.impl;

import com.example.petlog.dto.request.PetRequest;
import com.example.petlog.dto.response.PetResponse;
import com.example.petlog.entity.Pet;
import com.example.petlog.entity.Status;
import com.example.petlog.entity.User;
import com.example.petlog.exception.BusinessException;
import com.example.petlog.exception.ErrorCode;
import com.example.petlog.repository.PetRepository;
import com.example.petlog.repository.UserRepository;
import com.example.petlog.service.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;

    @Override
    // 펫 생성
    public PetResponse.CreatePetDto createPet(Long userId, PetRequest.CreatePetDto request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        //펫 이름 중복
        if (petRepository.existsByUserIdAndPetName(userId, request.getPetName())) {
            throw new BusinessException(ErrorCode.USER_ID_DUPLICATE);
        }

        Pet pet = Pet.builder()
                .user(user)
                .petName(request.getPetName())
                .genderType(request.getGenderType())
                .breed(request.getBreed())
                .age(request.getAge())
                .status(Status.ALIVE)
                .birth(request.getBirth())
                .species(request.getSpecies())
                .neutered(request.isNeutered())
                .profileImage(request.getProfileImage())
                .build();
        Pet savedPet = petRepository.save(pet);
        return PetResponse.CreatePetDto.fromEntity(savedPet);


    }

    @Override
    public PetResponse.UpdatePetDto updatePet(Long petId, PetRequest.@Valid UpdatePetDto request) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PET_NOT_FOUND));
        pet.updatePet(
                request.getPetName(),
                request.getBreed(),
                request.getGenderType(),
                request.getAge(),
                request.getBirth(),
                request.getSpecies(),
                request.isNeutered(),
                request.getProfileImage()
        );
        petRepository.save(pet);
        return PetResponse.UpdatePetDto.fromEntity(pet);
    }

    @Override
    public PetResponse.GetPetDto getPet(Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PET_NOT_FOUND));
        return PetResponse.GetPetDto.fromEntity(pet);
    }

    @Override
    public void deletePet(Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PET_NOT_FOUND));
        petRepository.delete(pet);

    }

    @Override
    public void lostPet(Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PET_NOT_FOUND));
        pet.lostPet();
        petRepository.save(pet);
    }
}
