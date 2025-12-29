package com.example.petlog.dto.response;

import com.example.petlog.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CoinLogResponse {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class CreateCoinLogDto {
        private CoinType type;
        private Long amount;
        private LocalDateTime createdAt;


        public static CreateCoinLogDto fromEntity(CoinLog coinLog) {

            return CreateCoinLogDto.builder()
                    .type(coinLog.getType())
                    .amount(coinLog.getAmount())
                    .createdAt(coinLog.getCreatedAt())
                    .build();
        }

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class CreateCoinLogDtoList {
        List<CreateCoinLogDto> coins;


        public static CreateCoinLogDtoList fromEntity(List<CoinLog> coinLogs) {

            List<CreateCoinLogDto> coins = coinLogs.stream()
                    .map(coinLog -> CreateCoinLogDto.fromEntity(coinLog))
                    .toList();
            return CreateCoinLogDtoList.builder()
                    .coins(coins)
                    .build();
        }

    }

}
