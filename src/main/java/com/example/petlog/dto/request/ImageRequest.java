package com.example.petlog.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ImageRequest {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class DeleteImageDto {
        @NotNull
        List<String> imageUrls;
    }
}
