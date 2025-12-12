package com.example.petlog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // 인증/인가 관련 (40X)
    AUTH_INVALID_CREDENTIALS("AUTH_001", "이메일 또는 비밀번호가 올바르지 않습니다", HttpStatus.UNAUTHORIZED),
    AUTH_TOKEN_EXPIRED("AUTH_002", "토큰이 만료되었습니다", HttpStatus.UNAUTHORIZED),
    AUTH_TOKEN_INVALID("AUTH_003", "유효하지 않은 토큰입니다", HttpStatus.UNAUTHORIZED),
    AUTH_ACCESS_DENIED("AUTH_004", "접근 권한이 없습니다", HttpStatus.FORBIDDEN),
    AUTH_ACCOUNT_LOCKED("AUTH_005", "계정이 잠겨있습니다", HttpStatus.FORBIDDEN),

    // 검증 관련 (40X)
    VALIDATION_ERROR("VALID_001", "입력값이 올바르지 않습니다", HttpStatus.BAD_REQUEST),
    REQUIRED_FIELD_MISSING("VALID_002", "필수 항목이 누락되었습니다", HttpStatus.BAD_REQUEST),
    INVALID_FORMAT("VALID_003", "형식이 올바르지 않습니다", HttpStatus.BAD_REQUEST),

    // 비즈니스 로직 관련 (40X)
    BUSINESS_RULE_VIOLATION("BIZ_001", "비즈니스 규칙 위반입니다", HttpStatus.BAD_REQUEST),
    OPERATION_NOT_ALLOWED("BIZ_002", "허용되지 않은 작업입니다", HttpStatus.BAD_REQUEST),

    //사용자 관련
    USER_NOT_FOUND("USER_001","사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    USER_ID_DUPLICATE("USER_002","중복된 아이디입니다.", HttpStatus.BAD_REQUEST),
    USER_NAME_DUPLICATE("USER_002","중복된 이름입니다.", HttpStatus.BAD_REQUEST),
    BIRTH_IS_NULL("USER_003","생일값이 존재하지 않니다.",HttpStatus.BAD_REQUEST),

    //펫 관련
    PET_NAME_DUPLICATE("PET_002","중복된 이름입니다.", HttpStatus.BAD_REQUEST),
    PET_NOT_FOUND("PET_002","펫을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    PET_COIN_NOT_ENOUGH("PET_003","펫코인이 부족합니다.", HttpStatus.BAD_REQUEST),

    //파일 관련
    FILE_NOT_EXIST("FILE_001","파일이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    INVALID_FILE_EXTENSION("FILE_002","잘못된 파일 확장자입니다.", HttpStatus.BAD_REQUEST),
    INVALID_URL_FORMAT("FILE_003","잘못된 URL 형식입니다.", HttpStatus.BAD_REQUEST),
    UPLOAD_FILE_IO_EXCEPTION("FILE_004","파일 업로드 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    DELETE_FILE_IO_EXCEPTION("FILE_005","파일 삭제 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    //아카이브 관련
    ARCHIVE_NOT_FOUND("ARCHIVE_001","보관함을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    // 서버 오류 (50X)
    INTERNAL_SERVER_ERROR("SERVER_001", "서버 내부 오류가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR),
    DATABASE_ERROR("SERVER_002", "데이터베이스 오류가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR);


    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }


}