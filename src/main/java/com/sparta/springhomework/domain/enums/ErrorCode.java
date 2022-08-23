package com.sparta.springhomework.domain.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {

  ENTITY_NOT_FOUND("NOT_FOUND", "데이터가 존재하지 않습니다."),
  INVALID_ERROR("INVALID_ERROR", "에러 발생!!!"),
  NOT_VALID_NICKNAME("NOT_VALID_NICKNAME", "닉네임 형식이 틀렸습니다."),
  NOT_VALID_PASSWORD("NOT_VALID_PASSWORD", "비밀번호 형식이 틀렸습니다."),
  NOT_SAME_PASSWORD("NOT_SAME_PASSWORD", "비밀번호와 비밀번호 확인이 일치하지 않습니다."),
  DUPLICATION_NICKNAME("DUPLICATION_NICKNAME", "중복된 닉네임입니다.");


  private final String code;
  private final String message;

  ErrorCode(String code, String message) {
    this.code = code;
    this.message = message;
  }
}
