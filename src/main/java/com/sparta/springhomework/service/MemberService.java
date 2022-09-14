package com.sparta.springhomework.service;

import com.sparta.springhomework.domain.request.MemberLogInRequestDto;
import com.sparta.springhomework.domain.request.MemberSignUpRequestDto;
import com.sparta.springhomework.domain.response.MemberLogInResponseDto;
import com.sparta.springhomework.domain.response.MemberSignUpResponseDto;
import javax.servlet.http.HttpServletResponse;


public interface MemberService {

  MemberSignUpResponseDto signUp(MemberSignUpRequestDto memberSignUpRequestDto);

//  MemberLogInResponseDto login(MemberLogInRequestDto memberLogInRequestDto);

  MemberLogInResponseDto login(MemberLogInRequestDto memberLogInRequestDto,
      HttpServletResponse response);


}
