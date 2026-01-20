package com.ecom.OrderService.feignClient;

import com.ecom.OrderService.dto.ProductResponse;
import com.ecom.OrderService.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "UserService",url = "http://localhost:8084")
public interface UserClient {

    @GetMapping("/users/{id}")
    public UserResponse getUser(@PathVariable UUID id);
}
