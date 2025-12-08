package com.example.petlog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "pets")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //보호자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //펫 이름
    @Column(nullable = false)
    private String petName;

    //종류
    @Enumerated(EnumType.STRING)
    private Species species;

    //품종
    @Column(nullable = false)
    private String breed;

    //성별
    @Enumerated(EnumType.STRING)
    private GenderType genderType;

    //중성화여부
    @Column
    private boolean neutered;

    //프로필 사진
    @Column(nullable = true)
    private String profileImage;

    //나이
    @Column(nullable = false)
    private Integer age;

    //생일
    @Column
    private LocalDateTime birth;

    //상태
    @Enumerated(EnumType.STRING)
    private Status status;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void updatePet(String petName, String breed, GenderType genderType, Integer age, LocalDateTime birth, Species species, boolean neutered, String profileImage) {
        this.petName = petName;
        this.breed = breed;
        this.genderType = genderType;
        this.age = age;
        this.birth = birth;
        this.species = species;
        this.neutered = neutered;
        this.profileImage = profileImage;

    }

    public void lostPet() {
        this.status = Status.LOST;
    }
}
