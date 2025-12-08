package com.example.petlog.controller;
import com.example.petlog.dto.request.PetRequest;
import com.example.petlog.dto.response.PetResponse;
import com.example.petlog.service.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;
    //펫 추가
    @PostMapping("/create")
    public ResponseEntity<PetResponse.CreatePetDto> createPet(@RequestHeader("X-USER-ID") Long userId, @Valid @RequestBody PetRequest.CreatePetDto request) {
        return ResponseEntity.ok(petService.createPet(userId, request));
    }

    @PostMapping("/{petId}")
    public ResponseEntity<PetResponse.UpdatePetDto> updatePet(@PathVariable Long petId, @Valid @RequestBody PetRequest.UpdatePetDto request) {
        return ResponseEntity.ok(petService.updatePet(petId, request));
    }
    @GetMapping("/{petId}")
    public ResponseEntity<PetResponse.GetPetDto> getPet(@PathVariable Long petId) {
        return ResponseEntity.ok(petService.getPet(petId));
    }

    @DeleteMapping("/{petId}")
    public ResponseEntity<String> deletePet(@PathVariable Long petId) {
        petService.deletePet(petId);
        return ResponseEntity.ok("펫이 삭제되었습니다.");
    }
    @PostMapping("/lost/{petId}")
    public ResponseEntity<String> lostPet(@PathVariable Long petId) {
        petService.lostPet(petId);
        return ResponseEntity.ok("상태 변경을 완료하였습니다.");
    }

}
