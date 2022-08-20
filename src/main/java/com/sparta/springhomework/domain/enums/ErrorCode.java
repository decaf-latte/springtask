package com.sparta.springhomework.domain.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {

    ENTITY_NOT_FOUND("NOT_FOUND", "데이터가 존재하지 않습니다."),
    INVALID_ERROR("INVALID_ERROR","에러 발생!!!");


    private final String code;
    private final String message;

    ErrorCode(String code, String message){
        this.code = code;
        this.message = message;
    }
}
