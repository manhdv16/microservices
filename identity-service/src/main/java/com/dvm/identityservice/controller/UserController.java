package com.dvm.identityservice.controller;

import com.dvm.identityservice.dto.request.UserCreationRequest;
import com.dvm.identityservice.dto.response.ApiResponse;
import com.dvm.identityservice.dto.response.UserResponse;
import com.dvm.identityservice.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }
    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUserById(@PathVariable String userId) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserById(userId))
                .build();
    }
    @DeleteMapping("/{userId}")
    ApiResponse<String> delete(@PathVariable String userId) {
        userService.delete(userId);
        return ApiResponse.<String>builder()
                .result("Deleted user with id: " + userId)
                .build();
    }
}
