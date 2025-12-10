package com.example.petlog.repository;

import com.example.petlog.entity.Archive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArchiveRepository extends JpaRepository<Archive, Long> {
    List<Archive> findAllByUserId(Long id);
    Optional<Archive> findById(Long id);
}
