package com.ecom.UserService.service;

import com.ecom.UserService.dto.CreateUserRequest;
import com.ecom.UserService.dto.LoginRequest;
import com.ecom.UserService.dto.RegisterResponse;
import com.ecom.UserService.entity.User;
import com.ecom.UserService.entity.UserStatus;
import com.ecom.UserService.jwt.JwtToken;
import com.ecom.UserService.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponse create(CreateUserRequest request){
        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .status(UserStatus.ACTIVE)
                .password(passwordEncoder.encode(request.password()))
                .createdAt(LocalDateTime.now())
                .build();

        User userRes = userRepository.save(user);
        String token = JwtToken.generateToken(user);
        RegisterResponse registerResponse = RegisterResponse.builder()
                .message("user registered successfully")
                .user(userRes)
                .token(token)
                .build();
        return registerResponse;
    }

    public User getUserById(UUID id){
        return userRepository.findById(id).orElseThrow(()->new RuntimeException("user not found"));
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email()).orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = JwtToken.generateToken(user);
        return  token;
    }
}
