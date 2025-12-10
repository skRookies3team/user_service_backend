package com.example.petlog.service.impl;

import com.example.petlog.dto.request.ArchiveRequest;
import com.example.petlog.dto.response.ArchiveResponse;
import com.example.petlog.entity.Archive;
import com.example.petlog.entity.User;
import com.example.petlog.exception.BusinessException;
import com.example.petlog.exception.ErrorCode;
import com.example.petlog.repository.ArchiveRepository;
import com.example.petlog.repository.UserRepository;
import com.example.petlog.service.ArchiveService;
import com.example.petlog.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArchiveServiceImpl implements ArchiveService {

    private final ArchiveRepository archiveRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    @Override
    public ArchiveResponse.CreateArchiveDtoList createArchive(ArchiveRequest.CreateArchiveDto request, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        //1. S3에 사진을 저장하고 url을 받음
        List<String> imageUrls = imageService.upload(request.getImages());

        //2. URL 리스트를 Archive 엔티티 리스트로 변환
        List<Archive> archives = imageUrls.stream()
                .map(url -> Archive.builder()
                        .imageUrl(url)
                        .user(user) // 또는 관련 Archive 엔티티를 연결
                        .build())
                .collect(Collectors.toList());

        // 3. saveAll을 사용하여 DB에 한 번에 저장 (배치 처리)
        archiveRepository.saveAll(archives);
        //dto로 변환하여 반환
        return ArchiveResponse.CreateArchiveDtoList.toList(archives);

    }

    @Override
    public ArchiveResponse.CreateArchiveDtoList getAllArchives(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        List<Archive> archives = archiveRepository.findAllByUserId(user.getId());
        return ArchiveResponse.CreateArchiveDtoList.toList(archives);
    }

    @Override
    public ArchiveResponse.CreateArchiveDto getArchive(Long archiveId) {
        Archive archive = archiveRepository.findById(archiveId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ARCHIVE_NOT_FOUND));
        return ArchiveResponse.CreateArchiveDto.fromEntity(archive);
    }
}
