//package com.sparta.springhomework.controller;
//
//import com.sparta.springhomework.domain.dto.MemberSignUpRequestDto;
//import com.sparta.springhomework.domain.dto.MemberSignUpResponseDto;
//import com.sparta.springhomework.domain.dto.TokenDto;
//import com.sparta.springhomework.domain.dto.TokenRequestDto;
//import com.sparta.springhomework.service.AuthService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/auth")
//@RequiredArgsConstructor
//public class AuthController {
//
//  private final AuthService authService;
//
//  @PostMapping("/signup")
//  public ResponseEntity<MemberSignUpResponseDto> signup(
//      @RequestBody MemberSignUpRequestDto memberSignUpRequestDto) {
//    return ResponseEntity.ok(authService.signup(memberSignUpRequestDto));
//  }
//
//  @PostMapping("/login")
//  public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto memberRequestDto) {
//    return ResponseEntity.ok(authService.login(memberRequestDto));
//  }
//
//  @PostMapping("/reissue")
//  public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
//    return ResponseEntity.ok(authService.reissue(tokenRequestDto));
//  }
//}