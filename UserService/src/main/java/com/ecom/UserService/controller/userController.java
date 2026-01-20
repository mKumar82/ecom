package com.ecom.UserService.controller;

import com.ecom.UserService.dto.CreateUserRequest;
import com.ecom.UserService.dto.LoginRequest;
import com.ecom.UserService.dto.LoginResponse;
import com.ecom.UserService.dto.RegisterResponse;
import com.ecom.UserService.entity.User;
import com.ecom.UserService.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/users")
public class userController {
    private final UserService userService;

    @PostMapping("/register")
    public RegisterResponse create(@RequestBody CreateUserRequest request){
        return userService.create(request);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable UUID id){
        return userService.getUserById(id);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        log.info("+++++++++++++++++++++++++++++++++++in");
        return  ResponseEntity.ok(new LoginResponse(userService.login(request)));
    }
}
