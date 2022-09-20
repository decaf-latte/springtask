package com.sparta.springhomework.controller;


import com.sparta.springhomework.domain.enums.ErrorCode;
import com.sparta.springhomework.dto.request.MemberLogInRequestDto;
import com.sparta.springhomework.dto.request.MemberSignUpRequestDto;
import com.sparta.springhomework.dto.response.MemberLogInResponseDto;
import com.sparta.springhomework.dto.response.MemberSignUpResponseDto;
import com.sparta.springhomework.dto.response.ResponseDto;
import com.sparta.springhomework.exception.CustomException;
import com.sparta.springhomework.repository.MemberRepository;
import com.sparta.springhomework.service.MemberService;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberController {

  private final MemberRepository memberRepository;

  private final MemberService memberService;

  //회원가입
  @PostMapping("/api/member/signup")
  public ResponseDto<MemberSignUpResponseDto> signUp(
      @RequestBody MemberSignUpRequestDto memberSignUpRequestDto) {

    MemberSignUpResponseDto memberSignUpResponseDto;
    try {
      memberSignUpResponseDto = memberService.signUp(memberSignUpRequestDto);

    } catch (CustomException e) {
      log.error(e.getMessage());
      return new ResponseDto<>(null, e.getErrorCode());
    } catch (Exception e) {
      log.error(e.getMessage());
      return new ResponseDto<>(null, ErrorCode.INVALID_ERROR);
    }

    return new ResponseDto<>(memberSignUpResponseDto);
  }

  //로그인
  @PostMapping("/api/member/login")
  public ResponseDto<MemberLogInResponseDto> login(
      @RequestBody MemberLogInRequestDto memberLogInRequestDto, HttpServletResponse response) {
    MemberLogInResponseDto memberLogInResponseDto;
    try {
      memberLogInResponseDto = memberService.login(memberLogInRequestDto, response);
    } catch (CustomException e) {
      log.error(e.getMessage());
      return new ResponseDto<>(null, e.getErrorCode());
    } catch (Exception e) {
      log.error(e.getMessage());
      return new ResponseDto<>(null, ErrorCode.INVALID_ERROR);
    }
    return new ResponseDto<>(memberLogInResponseDto);
  }
}
