package com.sparta.springhomework.exception;

import com.sparta.springhomework.domain.enums.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

  private ErrorCode errorCode;

  public CustomException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

}
