package com.example.petlog.service;

import com.example.petlog.dto.request.ArchiveRequest;
import com.example.petlog.dto.response.ArchiveResponse;

public interface ArchiveService {
    ArchiveResponse.CreateArchiveDtoList createArchive(ArchiveRequest.CreateArchiveDto request, Long userId);

    ArchiveResponse.CreateArchiveDtoList getAllArchives(Long userId);

    ArchiveResponse.CreateArchiveDto getArchive(Long archiveId);
}
