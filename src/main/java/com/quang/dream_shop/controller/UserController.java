package com.quang.dream_shop.controller;

import com.quang.dream_shop.dto.UserDto;
import com.quang.dream_shop.exception.AlreadyExistsException;
import com.quang.dream_shop.exception.ResourceNotFoundException;
import com.quang.dream_shop.model.User;
import com.quang.dream_shop.repository.UserRepository;
import com.quang.dream_shop.request.CreateUserRequest;
import com.quang.dream_shop.request.UpdateUserRequest;
import com.quang.dream_shop.response.ApiResponse;
import com.quang.dream_shop.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService userService;
    private final UserRepository userRepository;

//    public ResponseEntity<ApiResponse> getUserProfile() {
//        try {
//            var userProfile = userService.getAuthenticatedUserProfile();
//            return ResponseEntity.ok(new ApiResponse("User profile retrieved successfully", userProfile));
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body(new ApiResponse("Failed to retrieve user profile", e.getMessage()));
//        }
//    }

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(Long userId){
        try {
            User user = userService.getUserById(userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("User retrieved successfully", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse("User not found", e.getMessage()));
        }

    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest userRequest) {
        try {
            User user = userService.createUser(userRequest);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("User created successfully", userDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(400).body(new ApiResponse("User already exists", e.getMessage()));
        }
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest userRequest,@PathVariable Long userId) {
        try {
            User updatedUser = userService.updateUser(userRequest, userId);
            UserDto userDto = userService.convertUserToDto(updatedUser);
            return ResponseEntity.ok(new ApiResponse("User updated successfully", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse("User not found", e.getMessage()));
        }
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("User deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse("User not found", e.getMessage()));
        }
    }






}


