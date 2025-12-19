package com.example.petlog.service.impl;


import com.example.petlog.dto.response.CoinLogResponse;
import com.example.petlog.entity.CoinLog;
import com.example.petlog.entity.CoinType;
import com.example.petlog.entity.User;
import com.example.petlog.exception.BusinessException;
import com.example.petlog.exception.ErrorCode;
import com.example.petlog.repository.CoinLogRepository;
import com.example.petlog.repository.UserRepository;
import com.example.petlog.service.CoinLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CoinLogServiceImpl implements CoinLogService {

    private final CoinLogRepository coinLogRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CoinLogResponse.CreateCoinLogDto useCoin(User user, Long amount, CoinType type) {


        CoinLog coinLog = CoinLog.builder()
                .amount(amount)
                .user(user)
                .type(type)
                .createdAt(LocalDateTime.now())
                .build();
        coinLogRepository.save(coinLog);
        return CoinLogResponse.CreateCoinLogDto.fromEntity(coinLog);


    }

    @Override
    @Transactional
    public CoinLogResponse.CreateCoinLogDtoList getCoinLog(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        List<CoinLog> coinLogs = coinLogRepository.findAllByUserId(userId);
        return CoinLogResponse.CreateCoinLogDtoList.fromEntity(coinLogs);

    }


}
