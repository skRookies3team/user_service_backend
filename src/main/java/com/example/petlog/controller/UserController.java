package com.example.petlog.controller;

import com.example.petlog.dto.request.UserRequest;
import com.example.petlog.dto.response.UserResponse;
import com.example.petlog.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    //회원가입
    @PostMapping("/create")
    public ResponseEntity<UserResponse.CreateUserDto> createUser(@Valid @RequestBody UserRequest.CreateUserDto request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PatchMapping
    public ResponseEntity<UserResponse.UpdateUserDto> updateUser(@RequestHeader("X-USER-ID") Long userId, @Valid @RequestBody UserRequest.UpdateUserDto request) {
        return ResponseEntity.ok(userService.updateUser(userId, request));
    }

    @DeleteMapping("/me")
    public ResponseEntity<String> deleteUser(@RequestHeader("X-USER-ID") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("해당 회원 정보가 삭제되었습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserByUserId(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("해당 회원 정보가 삭제되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse.LoginDto> login(
            @RequestBody UserRequest.LoginDto loginRequestDto
    ) {
        UserResponse.LoginDto loginResponseDto = userService.login(loginRequestDto);
        return ResponseEntity.ok(loginResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse.GetUserDto> getUser(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PatchMapping("/me")
    public ResponseEntity<UserResponse.UpdateProfileDto> updateProfile(@RequestHeader("X-USER-ID") Long userId, @Valid @RequestBody UserRequest.UpdateProfileDto request) {
        return ResponseEntity.ok(userService.updateProfile(userId, request));
    }
}
