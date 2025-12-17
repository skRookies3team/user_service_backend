package com.example.petlog.entity;

import com.example.petlog.exception.BusinessException;
import com.example.petlog.exception.ErrorCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.security.auth.Subject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //아이디
    @Column(unique = true, nullable = false)
    private String email;

    //소설 아이디
    @Column(unique = true, nullable = false)
    private String social;

    //비밀번호
    @Column(nullable = false)
    private String password;

    //암호화된 비밀번호
    @Column(nullable = false)
    private String encryptedPwd;

    //사용자 이름(닉네임)
    @Column(nullable = false)
    private String username;
    //생일
    @Column(nullable = false)
    LocalDate birth;
    //상태메세지
    @Column
    private String statusMessage;

    //사용자 타입
    @Enumerated(EnumType.STRING)
    private UserType type;

    //성별
    @Enumerated(EnumType.STRING)
    private GenderType genderType;

    //프로필 사진
    @Column(nullable = true)
    private String profileImage;

    //나이
    @Column(nullable = false)
    private Integer age;

    //위도
    @Column(nullable = true)
    private Integer currentLat;

    //경도
    @Column(nullable = true)
    private Integer currentLng;

    //펫코인
    @Column(nullable = false)
    private Long petCoin;

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pet> pets = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Archive> archives = new ArrayList<>();


    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void updateUser(String username, int age, String profileImage, GenderType genderType) {

        this.username = username;
        this.age = age;
        this.profileImage = profileImage;
        this.genderType = genderType;
    }

    public void updateProfile(String username,String profileImage,String social) {
        this.username = username;
        this.profileImage = profileImage;
        this.social = social;
    }

    public void earnCoin(Long amount) {
        this.petCoin += amount;
    }
    public void redeemCoin(Long petCoin, Long amount) {
        if (petCoin < amount) {
            throw new BusinessException(ErrorCode.PET_COIN_NOT_ENOUGH);
        }
        this.petCoin -= amount;
    }
}
