package com.example.petlog.dto.response;

import com.example.petlog.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ArchiveResponse {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class CreateArchiveDto {
        //보관함 id
        private Long archiveId;
        //이미지 url
        private String url;
        //시간
        private LocalDateTime uploadTime;

        public static ArchiveResponse.CreateArchiveDto fromEntity(Archive archive) {

            return CreateArchiveDto.builder()
                    .archiveId(archive.getId())
                    .url(archive.getImageUrl())
                    .uploadTime(archive.getUploadTime())
                    .build();
        }

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class CreateArchiveDtoList {
        //보관함 id
        private List<CreateArchiveDto> archives;

        public static CreateArchiveDtoList toList(List<Archive> archives) {

            List<CreateArchiveDto> createArchiveDtoList = archives.stream()
                    .map(archive -> CreateArchiveDto.fromEntity(archive))
                    .toList();
            return CreateArchiveDtoList.builder()
                    .archives(createArchiveDtoList)
                    .build();
        }

    }

}
