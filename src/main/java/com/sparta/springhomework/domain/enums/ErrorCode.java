package com.sparta.springhomework.domain.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {

  ENTITY_NOT_FOUND("NOT_FOUND", "데이터가 존재하지 않습니다."),
  INVALID_ERROR("INVALID_ERROR", "에러 발생"),
  NOT_VALID_NICKNAME("NOT_VALID_NICKNAME", "닉네임 형식이 틀렸습니다."),
  NOT_VALID_PASSWORD("NOT_VALID_PASSWORD", "비밀번호 형식이 틀렸습니다."),
  NOT_SAME_PASSWORD("NOT_SAME_PASSWORD", "비밀번호와 비밀번호 확인이 일치하지 않습니다."),
  DUPLICATION_NICKNAME("DUPLICATION_NICKNAME", "중복된 닉네임입니다."),
  NICKNAME_NOT_EXIST("NICKNAME_NOT_EXIST", "사용자를 찾을 수 없습니다."),
  BAD_REQUEST("BAD_REQUEST", "토큰이 유효하지 않습니다."),
  BAD_TOKEN("BAD_TOKEN", "잘못된 토큰 정보입니다."),
  EXPIRED_TOKEN("EXPIRED_TOKEN", "만료된 JWT 토큰입니다."),
  WRONG_TOKEN("WRONG_TOKEN", "지원되지 않는 JWT 토큰입니다."),
  NOT_SAME_AUTHORITY("  NOT_SAME_AUTHORITY", "권한이 일치하지 않습니다"),
  NOT_SAME_MEMBER("NOT_SAME_MEMBER", "해당 작성자만 수정이 가능합니다."),
  REQUIRE_AUTHORITY("REQUIRE_AUTHORITY", "필요한 권한이 없습니다.");


  private final String code;
  private final String message;

  ErrorCode(String code, String message) {
    this.code = code;
    this.message = message;
  }
}
