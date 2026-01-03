package com.example.petlog.service;

import com.example.petlog.dto.response.CoinLogResponse;
import com.example.petlog.entity.Coin;
import com.example.petlog.entity.CoinType;
import com.example.petlog.entity.User;

public interface CoinLogService {
    CoinLogResponse.CreateCoinLogDto useCoin(User user, Long amount, CoinType type, Coin coin);
    CoinLogResponse.CreateCoinLogDtoList getCoinLog(Long userId);
    CoinLogResponse.CreateCoinLogDtoList getCoinAddLog(Long userId);
    CoinLogResponse.CreateCoinLogDtoList getCoinUseLog(Long userId);
}
