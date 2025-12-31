package com.example.petlog.service.impl;

import com.example.petlog.dto.request.ArchiveRequest;
import com.example.petlog.dto.response.UserResponse;
import com.example.petlog.entity.User;
import com.example.petlog.exception.BusinessException;
import com.example.petlog.exception.ErrorCode;
import com.example.petlog.service.AiService;
import io.swagger.v3.oas.annotations.OpenAPI31;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

    private final ChatClient chatClient;

    @Override
    public UserResponse.AnalyzeAnimalDto analyzeAnimal(MultipartFile photo) {
        return chatClient.prompt()
                .user(u -> u.text("사진 속 동물의 종과 품종을 분석해줘. " +
                                "1. 종(species)은 [강아지, 고양이, 토끼, 햄스터, 기니피그, 새, 물고기, 파충류, 기타] 중 하나로 선택해. " +
                                "2. 품종(breed)은 해당 종에 맞는 구체적인 이름을 한국어로 대답해줘. " +
                                "3. 만약 정확한 품종을 알 수 없다면 '기타'로 대답해줘.")

                        .media(MimeTypeUtils.IMAGE_PNG, photo.getResource()))
                .call()
                .entity(UserResponse.AnalyzeAnimalDto.class);

    }

}
