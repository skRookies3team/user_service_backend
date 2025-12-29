package com.example.petlog.entity;

import com.example.petlog.exception.BusinessException;
import com.example.petlog.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "coin_logs")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CoinLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //적립 타입
    @Enumerated(EnumType.STRING)
    private CoinType type;
    //변화량
    @Column(nullable = false)
    private Long amount;

    //적립일
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;



}
