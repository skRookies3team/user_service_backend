package com.example.petlog.controller;

import com.example.petlog.dto.response.ArchiveResponse;
import com.example.petlog.dto.response.CoinLogResponse;
import com.example.petlog.service.CoinLogService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coinlogs")
@RequiredArgsConstructor
public class CoinLogController {

    private final CoinLogService coinLogService;

    @Operation(summary = "코인 사용 내역 조회", description = "사용자의 코인 사용 내역을 조회합니다.")
    @GetMapping
    public ResponseEntity<CoinLogResponse.CreateCoinLogDtoList> getCoinLog(@RequestHeader("X-USER-ID") Long userId) {
        return ResponseEntity.ok(coinLogService.getCoinLog(userId));
    }
}
