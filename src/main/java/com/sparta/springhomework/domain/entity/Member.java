package com.sparta.springhomework.domain.entity;


import com.sparta.springhomework.domain.dto.MemberSignUpRequestDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true, nullable = false)
  private String nickname;

  @Column(nullable = false)
  private String password;

  public Member(String nickname, String password) {
    this.nickname = nickname;
    this.password = password;
  }

  public Member(MemberSignUpRequestDto memberSignUpRequestDto) {
    this.nickname = memberSignUpRequestDto.getNickname();
    this.password = memberSignUpRequestDto.getPassword();
  }

}
