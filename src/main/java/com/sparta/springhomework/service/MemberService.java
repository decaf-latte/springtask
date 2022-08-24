package com.sparta.springhomework.service;

import com.sparta.springhomework.domain.dto.MemberLogInRequestDto;
import com.sparta.springhomework.domain.dto.MemberLogInResponseDto;
import com.sparta.springhomework.domain.dto.MemberSignUpRequestDto;
import com.sparta.springhomework.domain.dto.MemberSignUpResponseDto;


public interface MemberService {

  MemberSignUpResponseDto signUp(MemberSignUpRequestDto memberSignUpRequestDto);

  MemberLogInResponseDto login(MemberLogInRequestDto memberLogInRequestDto);


}
