package com.example.petlog.util;

import com.example.petlog.exception.BusinessException;
import com.example.petlog.exception.ErrorCode;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Service
public class Utils {
    public Integer calculateAge(LocalDate birth) {
        if (birth == null) {
            throw new BusinessException(ErrorCode.BIRTH_IS_NULL);
        }

        // 2. 현재 날짜를 가져옴
        LocalDate today = LocalDate.now();

        // 3. 현재 날짜가 생일보다 이전인지 확인 (미래의 날짜라면 0 또는 예외 처리)
        if (birth.isAfter(today)) {
            // 생일이 미래라면 나이는 0이거나 유효하지 않다고 판단
            return 0;
        }

        // 4. Period.between() 메서드를 사용하여 두 날짜 사이의 기간(년, 월, 일)을 계산
        Period period = Period.between(birth, today);

        // 5. 기간(Period)에서 연도(years) 값만 가져옴 (만 나이)
        return period.getYears();
    }
}
