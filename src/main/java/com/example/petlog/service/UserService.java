package com.example.petlog.service;

import com.example.petlog.dto.request.UserRequest;
import com.example.petlog.dto.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserResponse.CreateUserDto createUser(UserRequest.CreateUserDto request);
    UserResponse.LoginDto login(UserRequest.LoginDto authRequest);
    UserResponse.AuthDto getUserDetailsByUserId(String userId);

    UserResponse.GetUserDto getUser(Long userId);

    UserResponse.UpdateUserDto updateUser(Long userId, UserRequest.UpdateUserDto request);

    void deleteUser(Long userId);

    UserResponse.UpdateProfileDto updateProfile(Long userId, UserRequest.@Valid UpdateProfileDto request);
}
