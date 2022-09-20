package com.sparta.springhomework.service;

import com.sparta.springhomework.dto.request.MemberLogInRequestDto;
import com.sparta.springhomework.dto.request.MemberSignUpRequestDto;
import com.sparta.springhomework.dto.response.MemberLogInResponseDto;
import com.sparta.springhomework.dto.response.MemberSignUpResponseDto;
import javax.servlet.http.HttpServletResponse;


public interface MemberService {

  MemberSignUpResponseDto signUp(MemberSignUpRequestDto memberSignUpRequestDto);

//  MemberLogInResponseDto login(MemberLogInRequestDto memberLogInRequestDto);

  MemberLogInResponseDto login(MemberLogInRequestDto memberLogInRequestDto,
      HttpServletResponse response);


}
