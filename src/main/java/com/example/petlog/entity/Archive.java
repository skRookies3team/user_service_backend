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
@Table(name = "archives")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Archive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //보호자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //이미지 url
    @Column(nullable = false)
    private String imageUrl;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime uploadTime;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
