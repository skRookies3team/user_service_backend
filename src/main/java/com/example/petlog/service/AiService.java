package com.example.petlog.service;

import com.example.petlog.dto.response.UserResponse;
import org.springframework.web.multipart.MultipartFile;

public interface AiService {
    UserResponse.AnalyzeAnimalDto analyzeAnimal(MultipartFile photo);
}
