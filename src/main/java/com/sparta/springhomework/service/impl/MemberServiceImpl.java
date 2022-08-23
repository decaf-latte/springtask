package com.sparta.springhomework.service.Impl;

import com.sparta.springhomework.domain.dto.MemberSignUpRequestDto;
import com.sparta.springhomework.domain.dto.MemberSignUpResponseDto;
import com.sparta.springhomework.domain.entity.Member;
import com.sparta.springhomework.domain.enums.ErrorCode;
import com.sparta.springhomework.exception.CustomException;
import com.sparta.springhomework.repository.MemberRepository;
import com.sparta.springhomework.service.MemberService;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
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

  @Override
  @Transactional
  public MemberSignUpResponseDto signUp(MemberSignUpRequestDto memberSignUpRequestDto) {
    //회원가입 데이터 검증
    vaildSignUpData(memberSignUpRequestDto);

    //동일한 nickname 있는 지 확인
    checkDuplicationNickname(memberSignUpRequestDto.getNickname());

    //암호화 된 패스워드 세팀
    memberSignUpRequestDto.setPassword(encryptPassword(memberSignUpRequestDto.getPassword()));

    Member member = new Member(memberSignUpRequestDto);
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
}
