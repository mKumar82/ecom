package com.ecom.UserService.dto;

import com.ecom.UserService.entity.User;
import lombok.Builder;

@Builder
public record RegisterResponse(
        String message,
        String token,
        User user
) {
}
