package com.example.petlog.service;

import com.example.petlog.dto.request.PetRequest;
import com.example.petlog.dto.response.PetResponse;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

public interface PetService {

    PetResponse.CreatePetDto createPet(MultipartFile multipartFile, Long userNum, PetRequest.CreatePetDto request);

    PetResponse.UpdatePetDto updatePet(Long userId, MultipartFile multipartFile, Long petId, PetRequest.UpdatePetDto request);

    PetResponse.GetPetDto getPet(Long petId);

    void deletePet(Long petId);

    void lostPet(Long petId);
}
