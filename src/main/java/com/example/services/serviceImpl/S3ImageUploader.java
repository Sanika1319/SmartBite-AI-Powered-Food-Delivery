package com.example.services.serviceImpl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.Exception.UserIdNotFoundException;
import com.example.entities.User;
import com.example.repository.UserRepository;
import com.example.services.ImageUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3ImageUploader implements ImageUploader {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AmazonS3 client;

    @Value("${app.s3.bucket}")
    private String bucketName;


    @Override
    public String uploadImage(MultipartFile image) {
        if(image == null){
            throw new RuntimeException("Image is null");
        }
        String originalFilename = image.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = "profile-images/"+ UUID.randomUUID()+extension;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(image.getSize());
        metadata.setContentType(image.getContentType());

        try {
            client.putObject(new PutObjectRequest(bucketName,fileName,image.getInputStream(),metadata));
            return "https://"+bucketName+".s3.amazonaws.com/"+fileName;
        }catch (IOException e){
            throw new RuntimeException("Fail to upload image: " +e.getMessage());
        }
    }

    @Override
    public String uploadUserProfileImage(Long userId, MultipartFile image) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserIdNotFoundException("User with id not found: " + userId));
        String imageUrl = uploadImage(image);
        user.setImgUrl(imageUrl);
        userRepository.save(user);
        return imageUrl;
    }

    public void deleteByUrl(String imageUrl) {

        String key = extractKeyFromUrl(imageUrl);

        client.deleteObject(new DeleteObjectRequest(bucketName, key));
    }

    public String extractKeyFromUrl(String imageUrl) {
        return imageUrl.substring(imageUrl.indexOf(".com/") + 5);
    }
}
