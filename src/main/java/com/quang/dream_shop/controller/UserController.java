package com.quang.dream_shop.controller;

import com.quang.dream_shop.response.ApiResponse;
import com.quang.dream_shop.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService userService;

    public ResponseEntity<ApiResponse> getUserProfile() {
        try {
            var userProfile = userService.getAuthenticatedUserProfile();
            return ResponseEntity.ok(new ApiResponse("User profile retrieved successfully", userProfile));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse("Failed to retrieve user profile", e.getMessage()));
        }
    }


}


