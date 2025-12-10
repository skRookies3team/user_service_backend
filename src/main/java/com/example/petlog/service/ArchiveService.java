package com.example.petlog.service;

import com.example.petlog.dto.request.ArchiveRequest;
import com.example.petlog.dto.response.ArchiveResponse;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface ArchiveService {
    ArchiveResponse.CreateArchiveDtoList createArchive(ArchiveRequest.CreateArchiveDto request, Long userId);

    ArchiveResponse.CreateArchiveDtoList getAllArchives(Long userId);

    ArchiveResponse.CreateArchiveDto getArchive(Long archiveId);

    void delete(Long userId, ArchiveRequest.DeleteArchiveDto request);
}
