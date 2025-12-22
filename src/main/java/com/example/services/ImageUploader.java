package com.example.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploader {
    String uploadImage(MultipartFile file);

    String uploadUserProfileImage(Long userId, MultipartFile image);

    String extractKeyFromUrl(String imageUrl);

    void deleteByUrl(String imageUrl);
}
