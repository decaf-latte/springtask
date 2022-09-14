package com.sparta.springhomework.domain.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberLogInRequestDto {

  @NotBlank
  private String nickname;
  @NotBlank
  private String password;

}
