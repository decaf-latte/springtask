package com.sparta.springhomework.service.impl;

import com.sparta.springhomework.domain.entity.Member;
import com.sparta.springhomework.domain.enums.Authority;
import com.sparta.springhomework.domain.enums.ErrorCode;
import com.sparta.springhomework.dto.TokenDto;
import com.sparta.springhomework.dto.request.MemberLogInRequestDto;
import com.sparta.springhomework.dto.request.MemberSignUpRequestDto;
import com.sparta.springhomework.dto.response.MemberLogInResponseDto;
import com.sparta.springhomework.dto.response.MemberSignUpResponseDto;
import com.sparta.springhomework.dto.response.ResponseDto;
import com.sparta.springhomework.exception.CustomException;
import com.sparta.springhomework.jwt.TokenProvider;
import com.sparta.springhomework.repository.MemberRepository;
import com.sparta.springhomework.service.MemberService;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

  private final MemberRepository memberRepository;
  //비밀번호 암호화
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  private final TokenProvider tokenProvider;

  @Override
  @Transactional
  public MemberSignUpResponseDto signUp(MemberSignUpRequestDto memberSignUpRequestDto) {
    //회원가입 데이터 검증
    vaildSignUpData(memberSignUpRequestDto);
    //동일한 nickname 있는 지 확인
    checkDuplicationNickname(memberSignUpRequestDto.getNickname());
    //암호화 된 패스워드 세팅
    memberSignUpRequestDto.setPassword(encryptPassword(memberSignUpRequestDto.getPassword()));

    Member member = new Member(memberSignUpRequestDto, Authority.ROLE_USER);

    member = memberRepository.save(member);

    return new MemberSignUpResponseDto(member);

  }


  //회원가입 데이터검증
  private void vaildSignUpData(MemberSignUpRequestDto memberSignUpRequestDto) {
    // 닉네임 : 최소 4자 이상, 12자 이하 알파벳 대소문자(a~z, A~Z), 숫자(0~9)
    if (!Pattern.matches("^[a-zA-Z0-9]{4,12}$", memberSignUpRequestDto.getNickname())) {
      throw new CustomException(ErrorCode.NOT_VALID_NICKNAME);
    }
    // 비밀번호 : 최소 4자 이상이며, 32자 이하 알파벳 소문자(a~z), 숫자(0~9)
    if (!Pattern.matches("^[a-z0-9]{4,32}$", memberSignUpRequestDto.getPassword())) {
      throw new CustomException(ErrorCode.NOT_VALID_PASSWORD);
    }
    //비밀번호 == 비밀번호확인?
    if (!Objects.equals(memberSignUpRequestDto.getPassword(),
        memberSignUpRequestDto.getPasswordConfirm())) {
      throw new CustomException(ErrorCode.NOT_SAME_PASSWORD);
    }
  }

  //기존 닉네임 존재하는지 확인
  private void checkDuplicationNickname(String nickname) {
    Optional<Member> optionalMember = memberRepository.findByNickname(nickname);
    if (optionalMember.isPresent()) {
      throw new CustomException(ErrorCode.DUPLICATION_NICKNAME);
    }
  }

  //비밀번호 암호화
  private String encryptPassword(String password) {
    return bCryptPasswordEncoder.encode(password);
  }

  //로그인
  @Override
  public MemberLogInResponseDto login(MemberLogInRequestDto memberLogInRequestDto,
      HttpServletResponse response) {
    //회원정보 가져오기 - 없으면 error
    Member member = memberRepository.findByNickname(memberLogInRequestDto.getNickname())
        .orElseThrow(() -> new CustomException(ErrorCode.NICKNAME_NOT_EXIST));

    //bCryptPasswordEncoder의 입력된 패스워드와 db의 암호화 된 패스워드 비교
    if (!bCryptPasswordEncoder.matches(memberLogInRequestDto.getPassword(), member.getPassword())) {
      throw new CustomException(ErrorCode.NICKNAME_NOT_EXIST);
    }

    TokenDto tokenDto = tokenProvider.generateTokenDto(member);
    tokenToHeaders(tokenDto, response);

    return new MemberLogInResponseDto(member);
  }

  public ResponseDto<?> logout(HttpServletRequest request) {
    if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
      return new ResponseDto<>(ErrorCode.BAD_REQUEST);
    }
    Member member = tokenProvider.getMemberFromAuthentication();
    if (null == member) {
      return new ResponseDto<>(ErrorCode.NICKNAME_NOT_EXIST);
    }

    return tokenProvider.deleteRefreshToken(member);
  }

  public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
    response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
    response.addHeader("Refresh-Token", tokenDto.getRefreshToken());
    response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
  }

}





