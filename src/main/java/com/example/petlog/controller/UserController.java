package com.example.petlog.controller;

import com.example.petlog.dto.request.UserRequest;
import com.example.petlog.dto.response.UserResponse;
import com.example.petlog.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping(value = "/signup",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserResponse.CreateUserDto> createUser(@RequestPart(value = "multipartFile") List<MultipartFile> multipartFile, @Valid @RequestPart("request") UserRequest.CreateUserAndPetDto request) {
        return ResponseEntity.ok(userService.createUser(multipartFile, request));
    }

    @Operation(summary = "회원 정보 수정", description = "현재 로그인된 사용자 정보를 수정합니다.")
    @PatchMapping
    public ResponseEntity<UserResponse.UpdateUserDto> updateUser(@RequestHeader("X-USER-ID") Long userId, @Valid @RequestBody UserRequest.UpdateUserDto request) {
        return ResponseEntity.ok(userService.updateUser(userId, request));
    }

    @Operation(summary = "회원 탈퇴 (본인)", description = "현재 로그인된 사용자를 탈퇴 처리합니다.")
    @DeleteMapping("/me")
    public ResponseEntity<String> deleteUser(@RequestHeader("X-USER-ID") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("해당 회원 정보가 삭제되었습니다.");
    }

    @Operation(summary = "특정 ID의 회원 탈퇴", description = "특정 ID의 사용자를 탈퇴 처리합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserByUserId(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("해당 회원 정보가 삭제되었습니다.");
    }

    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인하고 JWT 토큰을 발급받습니다.")
    @PostMapping("/login")
    public ResponseEntity<UserResponse.LoginDto> login(
            @RequestBody UserRequest.LoginDto loginRequestDto
    ) {
        UserResponse.LoginDto loginResponseDto = userService.login(loginRequestDto);
        return ResponseEntity.ok(loginResponseDto);
    }

    @Operation(summary = "회원 정보 조회", description = "특정 ID의 사용자 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse.GetUserDto> getUser(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @Operation(summary = "프로필 수정", description = "현재 로그인된 사용자의 프로필 정보를 수정합니다.")
    @PatchMapping("/me")
    public ResponseEntity<UserResponse.UpdateProfileDto> updateProfile(@RequestHeader("X-USER-ID") Long userId, @Valid @RequestBody UserRequest.UpdateProfileDto request) {
        return ResponseEntity.ok(userService.updateProfile(userId, request));
    }

    @Operation(summary = "코인 수량 조회", description = "특정 ID의 사용자 코인 수량을 조회합니다.")
    @GetMapping("/{id}/coin")
    public ResponseEntity<UserResponse.CoinDto> getCoin(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(userService.getCoin(userId));
    }

    @Operation(summary = "코인 적립", description = "특정 ID의 사용자 코인을 적립합니다.")
    @PostMapping("/{id}/coin/earn")
    public ResponseEntity<UserResponse.CoinDto> earnCoin(@PathVariable("id") Long userId, @Valid @RequestBody UserRequest.CoinDto request) {
        return ResponseEntity.ok(userService.earnCoin(userId, request));
    }

    @Operation(summary = "코인 사용", description = "특정 ID의 사용자 코인을 사용합니다.")
    @PostMapping("/{id}/coin/redeem")
    public ResponseEntity<UserResponse.CoinDto> redeemCoin(@PathVariable("id") Long userId, @Valid @RequestBody UserRequest.CoinDto request) {
        return ResponseEntity.ok(userService.redeemCoin(userId, request));
    }
}
