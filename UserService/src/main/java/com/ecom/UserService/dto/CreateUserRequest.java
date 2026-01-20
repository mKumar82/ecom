package com.ecom.UserService.dto;

public record CreateUserRequest(
        String name,
        String email,
        String password
) {
}
