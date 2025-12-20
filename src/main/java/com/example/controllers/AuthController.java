package com.example.controllers;

import com.example.entities.User;
import com.example.repository.UserRepository;
import com.example.request.LoginRequest;
import com.example.services.EmailService;
import com.example.services.UserService;
import com.example.services.serviceImpl.CustomUserDetailsService;
import com.example.services.serviceImpl.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;
    @PostMapping("/register")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken
                        (loginRequest.getEmail(), loginRequest.getPassword()));
        if(authenticate.isAuthenticated()){
            String jwt = jwtService
                    .generateToken(customUserDetailsService.loadUserByUsername(loginRequest.getEmail()));
            try{
                User user = userRepository.findByEmail(loginRequest.getEmail()).get();
                emailService.sendEmail(loginRequest.getEmail(),"Welcome back to Smart Bite! \uD83D\uDE0B","Hi "+user.getName()+" You’ve successfully logged in to your account. Your next delicious meal is just a few taps away. ");
            }catch (Exception e){
                throw new RuntimeException("Problem To Send Email");
            }
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }else {
            return  new ResponseEntity<>("Unauthorized User",HttpStatus.UNAUTHORIZED);
        }
    }
}
