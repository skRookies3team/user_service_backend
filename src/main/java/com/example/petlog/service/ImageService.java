package com.example.petlog.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    List<String> upload(List<MultipartFile> files);
    void delete(List<String> imageUrls);
}
