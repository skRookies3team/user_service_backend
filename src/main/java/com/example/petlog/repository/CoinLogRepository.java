package com.example.petlog.repository;

import com.example.petlog.entity.Archive;
import com.example.petlog.entity.Coin;
import com.example.petlog.entity.CoinLog;
import com.example.petlog.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CoinLogRepository extends JpaRepository<CoinLog, Long> {
    List<CoinLog> findAllByUserId(Long userId);
    List<CoinLog> findAllByUserIdAndCoin(Long userId, Coin coin);
}
