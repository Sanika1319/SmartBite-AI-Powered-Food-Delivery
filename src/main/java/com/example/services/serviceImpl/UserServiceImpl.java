package com.example.services.serviceImpl;

import com.example.Exception.EmailNotFoundException;
import com.example.Exception.InvalidPasswordException;
import com.example.Exception.UserIdNotFoundException;
import com.example.dto.UserDto;
import com.example.entities.Cart;
import com.example.entities.ChangePassword;
import com.example.entities.Role;
import com.example.entities.User;
import com.example.repository.CartRepository;
import com.example.repository.UserRepository;
import com.example.services.EmailService;
import com.example.services.ImageUploader;
import com.example.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ImageUploader imageUploader;

    public User userDtoToUser(UserDto userDto){
        return modelMapper.map(userDto,User.class);
    }


//    user ---> UserDto

    public UserDto userToUserDto(User user){
        return modelMapper.map(user,UserDto.class);
    }



    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        User saved = userRepository.save(user);

//         creating new cart for every user
        Cart cart = new Cart();
        cart.setUser(saved);
        cart.setQuantity(0);
        cart.setTotalAmount(0);
        Cart cart1 = cartRepository.save(cart);

        saved.setCart(cart1);

        try{
            emailService.sendEmail(user.getEmail(),"Registration Successfully Done","Hi"+user.getName()+"Welcome To SmartBite");
        }catch (Exception e){
            throw new RuntimeException("Problem To Send Email");
        }
        return saved;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserIdNotFoundException("User Not Found: " + userId));
        return userToUserDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User byEmail = userRepository.findByEmail(email).orElseThrow(()->new  EmailNotFoundException("User Not Found with email id: "+email));
        return userToUserDto(byEmail);
    }

    @Override
    public UserDto updateProfile(Long userId, UserDto userDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserIdNotFoundException("User Not Found: " + userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setImgUrl(user.getImgUrl());
        User updatedUser = userRepository.save(user);
        try{

            emailService.sendEmail(user.getEmail(),"Your Smart Bite Profile Has Been Updated \uD83C\uDF89","Hi "+user.getName()+" Great news! \uD83C\uDF89\n" +
                    "\n" +
                    "Your Smart Bite profile has been updated successfully. Any changes you made—such as your personal details or profile picture—are now live and ready to use.");
        }catch (Exception e){
            throw new RuntimeException("Problem To Send Email");
        }
        return userToUserDto(updatedUser);
    }

    @Override
    public void updatePassword(Long userId, ChangePassword changePassword) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserIdNotFoundException("User Not Found: " + userId));
        if (!passwordEncoder.matches(changePassword.getOldPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Old password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
         userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserIdNotFoundException("User Not Found: " + userId));
        userRepository.delete(user);
    }

    @Override
    public void updateProfileImage(Long userId, MultipartFile file) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserIdNotFoundException("User Not Found: " + userId));
        if(user.getImgUrl() != null && !user.getImgUrl().isBlank()){
            imageUploader.deleteByUrl(user.getImgUrl());
        }
        String uploadedImage = imageUploader.uploadUserProfileImage(userId, file);
        user.setImgUrl(uploadedImage);
        userRepository.save(user);
    }
}
