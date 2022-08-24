package com.sparta.springhomework.service;

import com.sparta.springhomework.domain.dto.MemberLogInRequestDto;
import com.sparta.springhomework.domain.dto.MemberLogInResponseDto;
import com.sparta.springhomework.domain.dto.MemberSignUpRequestDto;
import com.sparta.springhomework.domain.dto.MemberSignUpResponseDto;
import javax.servlet.http.HttpServletResponse;


public interface MemberService {

  MemberSignUpResponseDto signUp(MemberSignUpRequestDto memberSignUpRequestDto);

//  MemberLogInResponseDto login(MemberLogInRequestDto memberLogInRequestDto);

  MemberLogInResponseDto login(MemberLogInRequestDto memberLogInRequestDto,
      HttpServletResponse response);


}
