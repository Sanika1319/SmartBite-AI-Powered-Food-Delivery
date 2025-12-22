package com.example.controllers;

import com.example.dto.UserDto;
import com.example.entities.ChangePassword;
import com.example.entities.User;
import com.example.services.ImageUploader;
import com.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private ImageUploader imageUploader;

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/getUserById/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId){
        return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
    }

    @GetMapping("/getUserByEmail")
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam String email){
        return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
    }

    @PostMapping("/updateProfile/{userId}")
    public ResponseEntity<UserDto> updateProfile(@PathVariable Long userId, @RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.updateProfile(userId,userDto),HttpStatus.OK);
    }

    @PutMapping("/updatePassword/{userId}")
    public ResponseEntity<?> updatePassword(@PathVariable Long userId,
                                            @RequestBody ChangePassword changePassword){
        userService.updatePassword(userId,changePassword);
        return new ResponseEntity<>("Password Updated Successfully",HttpStatus.OK);
    }

    @PostMapping("/uploadImage/s3/{userId}")
    public ResponseEntity<?> uploadProfileImage(@PathVariable Long userId, @RequestParam MultipartFile file){
        String imageUrl = imageUploader.uploadUserProfileImage(userId, file);
        return ResponseEntity.ok(
                Map.of("message","Profile image uploaded successfully",
                        "Image Url",imageUrl)
        );
    }

    @PutMapping("/updateProfileImage/{userId}")
    public ResponseEntity<?> updateProfileImage(@PathVariable Long userId, @RequestParam MultipartFile file){
        userService.updateProfileImage(userId,file);
        return ResponseEntity.ok(
                Map.of("message","Profile image updated successfully")
        );
    }
    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return  ResponseEntity.ok("user deleted successfully");
    }
}
