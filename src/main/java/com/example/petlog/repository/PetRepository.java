package com.example.petlog.repository;

import com.example.petlog.entity.Pet;
import com.example.petlog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    boolean existsByUserIdAndPetName(Long userId, String petName);
    List<Pet> findAllByUserId(Long userId);

}
