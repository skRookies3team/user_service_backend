package com.example.petlog.repository;

import com.example.petlog.entity.Archive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArchiveRepository extends JpaRepository<Archive, Long> {
    List<Archive> findAllByUserId(Long id);
    Optional<Archive> findById(Long id);
    @Query("SELECT imageUrl FROM Archive a WHERE a.id IN :archiveIds")
    List<String> findImageUrlsByArchiveIds(@Param("archiveIds") List<Long> archiveIds);
}
