package com.example.petlog.service;

import com.example.petlog.dto.request.UserRequest;
import com.example.petlog.dto.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    UserResponse.CreateUserDto createUser(MultipartFile userProfile, MultipartFile petProfile, UserRequest.CreateUserAndPetDto request);
    UserResponse.LoginDto login(UserRequest.LoginDto authRequest);
    UserResponse.AuthDto getUserDetailsByUserId(String userId);

    UserResponse.GetUserDto getUser(Long userId);

    UserResponse.UpdateUserDto updateUser(Long userId, UserRequest.UpdateUserDto request);

    void deleteUser(Long userId);

    UserResponse.UpdateProfileDto updateProfile(Long userId, MultipartFile userProfile, UserRequest.UpdateProfileDto request);

    UserResponse.CoinDto getCoin(Long userId);

    UserResponse.CoinLogDto earnCoin(Long userId, UserRequest.@Valid CoinDto request);

    UserResponse.CoinLogDto redeemCoin(Long userId, UserRequest.@Valid CoinDto request);

    UserResponse.GetSearchedUserDtoList searchUsersWithSocial(String keyword);
}
