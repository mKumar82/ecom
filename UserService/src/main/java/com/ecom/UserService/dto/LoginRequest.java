package com.ecom.UserService.dto;

public record LoginRequest(
        String email,
        String password
) {
}
