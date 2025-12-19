package com.example.petlog.service;

import com.example.petlog.dto.response.CoinLogResponse;
import com.example.petlog.entity.CoinType;
import com.example.petlog.entity.User;

public interface CoinLogService {
    CoinLogResponse.CreateCoinLogDto useCoin(User user, Long amount, CoinType type);
    CoinLogResponse.CreateCoinLogDtoList getCoinLog(Long userId);
}
