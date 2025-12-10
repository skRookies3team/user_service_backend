package com.example.petlog.dto.request;

import com.example.petlog.dto.response.PetResponse;
import com.example.petlog.entity.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class ArchiveRequest {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CreateArchiveDto {
        @NotNull
        List<MultipartFile> images;


    }
}
