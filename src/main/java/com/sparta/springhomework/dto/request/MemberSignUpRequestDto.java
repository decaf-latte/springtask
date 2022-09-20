package com.sparta.springhomework.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public class MemberSignUpRequestDto {

  private String nickname;
  private String password;

  private String passwordConfirm;

  public void setPassword(String password) {
    this.password = password;
  }

}
