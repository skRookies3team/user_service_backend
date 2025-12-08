package com.example.petlog.service;

import com.example.petlog.dto.request.PetRequest;
import com.example.petlog.dto.response.PetResponse;
import jakarta.validation.Valid;

public interface PetService {

    PetResponse.CreatePetDto createPet(Long userNum, PetRequest.CreatePetDto request);

    PetResponse.UpdatePetDto updatePet(Long petId, PetRequest.@Valid UpdatePetDto request);

    PetResponse.GetPetDto getPet(Long petId);

    void deletePet(Long petId);

    void lostPet(Long petId);
}
