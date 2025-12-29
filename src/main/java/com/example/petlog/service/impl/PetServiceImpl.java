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
import com.example.petlog.service.ImageService;
import com.example.petlog.service.PetService;
import com.example.petlog.util.Utils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final ImageService imageService;
    private final Utils utils;
    private final String petBaseUrl = "https://petlog-images-bucket.s3.ap-northeast-2.amazonaws.com/7593d55c-0_%ED%8E%AB%EA%B8%B0%EB%B3%B8%ED%94%84%EB%A1%9C%ED%95%84.jpg";
    @Transactional
    @Override
    // 펫 생성
    public PetResponse.CreatePetDto createPet(MultipartFile petProfile, Long userId, PetRequest.CreatePetDto request) {

        log.error("=== CREATE PET START ===");
        log.error("userId = {}", userId);
        log.error("petProfile = {}",
                petProfile == null ? "null" : petProfile.getOriginalFilename());
        log.error("birth = {}", request.getBirth());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        //펫 이름 중복
        if (petRepository.existsByUserIdAndPetName(userId, request.getPetName())) {
            throw new BusinessException(ErrorCode.PET_NAME_DUPLICATE);
        }
        String profileImage = petBaseUrl;
        if (petProfile != null && !petProfile.isEmpty()) {
            List<MultipartFile> petProfiles = List.of(petProfile);
            List<String> urls = imageService.upload(petProfiles);
            profileImage = urls.get(0);
        }

        Integer age = utils.calculateAge(request.getBirth());

        Pet pet = PetRequest.CreatePetDto.toEntity(user, request, age, profileImage);
        Pet savedPet = petRepository.save(pet);
        return PetResponse.CreatePetDto.fromEntity(savedPet);


    }

    @Transactional
    @Override
    public PetResponse.UpdatePetDto updatePet(Long userId, MultipartFile petProfile, Long petId, PetRequest.@Valid UpdatePetDto request) {

        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PET_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        //펫 이름 중복
        if (petRepository.existsByUserIdAndPetName(userId, request.getPetName()) && !pet.getPetName().equals(pet.getPetName())) {
            throw new BusinessException(ErrorCode.PET_NAME_DUPLICATE);
        }
        String profileImage = pet.getProfileImage();
        if (petProfile != null && !petProfile.isEmpty()) {
            List<MultipartFile> petProfiles = List.of(petProfile);
            List<String> urls = imageService.upload(petProfiles);
            profileImage = urls.get(0);
        }

        pet.updatePet(
                request.getPetName(),
                request.getBreed(),
                request.getGenderType(),
                request.getAge(),
                request.getBirth(),
                request.getSpecies(),
                request.isNeutered(),
                profileImage,
                request.isVaccinated()
        );
        petRepository.save(pet);
        return PetResponse.UpdatePetDto.fromEntity(pet);
    }
    @Transactional(readOnly = true)
    @Override
    public PetResponse.GetPetDto getPet(Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PET_NOT_FOUND));
        return PetResponse.GetPetDto.fromEntity(pet);
    }
    @Transactional
    @Override
    public void deletePet(Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PET_NOT_FOUND));
        petRepository.delete(pet);

    }
    @Transactional
    @Override
    public void lostPet(Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PET_NOT_FOUND));
        pet.lostPet();
        petRepository.save(pet);
    }
}
