package com.example.petlog.dto.request;

import com.example.petlog.entity.GenderType;
import com.example.petlog.entity.Species;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public class ArchiveRequest {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CreateArchiveDto {


    }
}
