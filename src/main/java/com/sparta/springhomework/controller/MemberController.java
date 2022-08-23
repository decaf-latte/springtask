package com.sparta.springhomework.controller;


import com.sparta.springhomework.domain.dto.MemberSignUpRequestDto;
import com.sparta.springhomework.domain.dto.MemberSignUpResponseDto;
import com.sparta.springhomework.domain.dto.ResponseDto;
import com.sparta.springhomework.domain.enums.ErrorCode;
import com.sparta.springhomework.exception.CustomException;
import com.sparta.springhomework.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberController {


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

}
