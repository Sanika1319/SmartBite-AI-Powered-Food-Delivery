package com.example.services;

import com.example.dto.UserDto;
import com.example.entities.ChangePassword;
import com.example.entities.User;

import java.util.List;

public interface UserService {

//    Registration
    User saveUser(User user);

    List<User> getAllUsers();

    UserDto getUserById(Long userId);

    UserDto getUserByEmail(String email);

    UserDto updateProfile(Long userId, UserDto userDto);

    void updatePassword(Long userId, ChangePassword changePassword);

    void deleteUser(Long userId);


}
