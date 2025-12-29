package com.example.petlog.service.impl;

import com.example.petlog.dto.request.UserRequest;
import com.example.petlog.dto.response.CoinLogResponse;
import com.example.petlog.dto.response.PetResponse;
import com.example.petlog.dto.response.UserResponse;
import com.example.petlog.entity.Pet;
import com.example.petlog.entity.User;
import com.example.petlog.entity.UserType;
import com.example.petlog.exception.BusinessException;
import com.example.petlog.exception.ErrorCode;
import com.example.petlog.repository.PetRepository;
import com.example.petlog.repository.UserRepository;
import com.example.petlog.security.jwt.UserInfoDetails;
import com.example.petlog.service.CoinLogService;
import com.example.petlog.service.ImageService;
import com.example.petlog.service.PetService;
import com.example.petlog.service.UserService;
import com.example.petlog.util.Utils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final CoinLogService coinLogService;
    private final PetService petService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ImageService imageService;
    private final Utils utils;
    private final Environment env;
    private final String userBaseUrl = "https://petlog-images-bucket.s3.ap-northeast-2.amazonaws.com/af4bbf57-a_%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202025-12-16%20161002.png";

    @Transactional
    @Override
    // 유저 생성
    public UserResponse.CreateUserDto createUser(MultipartFile userProfile, MultipartFile petProfile, UserRequest.CreateUserAndPetDto request) {
        log.error("회원가입 시작");
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getUser().getPassword());
        //아이디 중복
        if (userRepository.existsByEmail(request.getUser().getEmail())) {
            throw new BusinessException(ErrorCode.USER_ID_DUPLICATE);
        }
        //소셜 아이디 중복
        if (userRepository.existsBySocial(request.getUser().getSocial())) {
            throw new BusinessException(ErrorCode.USER_SOCIAL_DUPLICATE);
        }

        String profileImage = userBaseUrl;
        if (userProfile != null && !userProfile.isEmpty()) {
            List<MultipartFile> userProfiles = List.of(userProfile);
            List<String> urls = imageService.upload(userProfiles);
            profileImage = urls.get(0);
        }

        Integer age = utils.calculateAge(request.getUser().getBirth());

        User user = User.builder()
                .email(request.getUser().getEmail())
                .social(request.getUser().getSocial())
                .password(request.getUser().getPassword())
                .encryptedPwd(encodedPassword)
                .profileImage(profileImage)
                .genderType(request.getUser().getGenderType())
                .petCoin(0L)
                .birth(request.getUser().getBirth())
                .age(age)
                .type(UserType.USER)
                .username(request.getUser().getUsername())
                .build();
        User savedUser = userRepository.save(user);
        log.error("userId = {}", savedUser.getId());

        if (request.getPet() != null) {
            petService.createPet(petProfile, savedUser.getId(), request.getPet());

        }
        return UserResponse.CreateUserDto.fromEntity(savedUser);
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponse.AuthDto getUserDetailsByUserId(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return UserResponse.AuthDto.fromEntity(user);
    }
    @Transactional
    @Override
    public UserResponse.LoginDto login(UserRequest.LoginDto authRequest) {
        // 1. AuthenticationManager를 사용하여 사용자 인증 시도
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword());
        // Spring Security 인증 체인 실행(UserDetails, PasswordEncoder 동작)
        Authentication authentication = authenticationManager.authenticate(
                authToken);
        // 2. 인증 성공 후 토큰 생성 및 응답 DTO 빌드
        if (authentication.isAuthenticated()) {

            String email = ((UserInfoDetails) authentication.getPrincipal()).getUsername();
            UserResponse.AuthDto userDetails = getUserDetailsByUserId(email);
            String token = generateJwtToken(userDetails);
            return UserResponse.LoginDto.fromEntity(token, userDetails);
        } else {
            throw new BusinessException(ErrorCode.AUTH_INVALID_CREDENTIALS);
        }


    }
    @Transactional
    //jwt 토큰 생성 메서드
    private String generateJwtToken(UserResponse.AuthDto userDetails) {
        // 환경 변수에서 Secret Key와 만료 시간 가져오기
        byte[] secretKeyBytes = Objects.requireNonNull(env.getProperty("token.secret")).getBytes(StandardCharsets.UTF_8);
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

        Instant now = Instant.now();
        Long expirationTime = Long.parseLong(Objects.requireNonNull(env.getProperty("token.expiration-time")));
        String userId = String.valueOf(userDetails.getUserId());
        String username = userDetails.getUsername();
        // JWT 토큰 생성
        return Jwts.builder()
                .subject(userId)
                .claim("username",username)
                .expiration(Date.from(now.plusMillis(expirationTime)))
                .issuedAt(Date.from(now))
                .signWith(secretKey)
                .compact();
    }
    @Transactional(readOnly = true)
    @Override
    // 유저 정보, 반려견 정보 반환
    public UserResponse.GetUserDto getUser(Long userId) {
        // 아이디 값으로 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        //펫 정보 조회
        List<Pet> pets = petRepository.findAllByUserId(userId);
        List<PetResponse.GetPetDto> petList = pets.stream().map(PetResponse.GetPetDto::fromEntity).toList();

        return UserResponse.GetUserDto.fromEntity(user,petList);
    }
    @Transactional
    @Override
    public UserResponse.UpdateUserDto updateUser(Long userId, UserRequest.UpdateUserDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        user.updateUser(
                request.getUsername(),
                request.getAge(),
                request.getProfileImage(),
                request.getGenderType()
        );
        userRepository.save(user);
        return UserResponse.UpdateUserDto.fromEntity(user);

    }
    @Transactional
    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        userRepository.delete(user);
    }
    @Transactional
    @Override
    public UserResponse.UpdateProfileDto updateProfile(Long userId,MultipartFile userProfile, UserRequest.UpdateProfileDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        String profileImage = user.getProfileImage();
        if (userProfile != null && !userProfile.isEmpty()) {
            List<MultipartFile> userProfiles = List.of(userProfile);
            List<String> urls = imageService.upload(userProfiles);
            profileImage = urls.get(0);
        }

        user.updateProfile(
                request.getUsername(),
                profileImage,
                request.getSocial()
        );
        userRepository.save(user);
        return UserResponse.UpdateProfileDto.fromEntity(user);
    }
    @Transactional
    @Override
    public UserResponse.CoinDto getCoin(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        return UserResponse.CoinDto.fromEntity(user);
    }
    @Transactional
    @Override
    public UserResponse.CoinLogDto earnCoin(Long userId, UserRequest.CoinDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        user.earnCoin(request.getAmount());
        userRepository.save(user);
        CoinLogResponse.CreateCoinLogDto coinResponse = coinLogService.useCoin(user, request.getAmount(), request.getType());
        return UserResponse.CoinLogDto.fromEntity(user, coinResponse.getAmount(), coinResponse.getType(), coinResponse.getCreatedAt());

    }
    @Transactional
    @Override
    public UserResponse.CoinLogDto redeemCoin(Long userId, UserRequest.@Valid CoinDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        user.redeemCoin(user.getPetCoin(), request.getAmount());
        userRepository.save(user);
        CoinLogResponse.CreateCoinLogDto coinResponse = coinLogService.useCoin(user, -request.getAmount(), request.getType());
        return UserResponse.CoinLogDto.fromEntity(user,coinResponse.getAmount(), coinResponse.getType(), coinResponse.getCreatedAt());
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponse.GetSearchedUserDtoList searchUsersWithSocial(String keyword) {
        List<User> users = userRepository.findBySocialContaining(keyword);
        return UserResponse.GetSearchedUserDtoList.fromEntity(users);

    }


}
