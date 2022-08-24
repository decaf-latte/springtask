package com.sparta.springhomework.domain.entity;


import com.sparta.springhomework.domain.dto.MemberSignUpRequestDto;
import com.sparta.springhomework.domain.enums.Authority;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "member")
public class Member extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true, nullable = false)
  private String nickname;

  @Column(nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  private Authority authority;

  public Member(String nickname, String password, Authority authority) {
    this.nickname = nickname;
    this.password = password;
    this.authority = authority;
  }

  public Member(MemberSignUpRequestDto memberSignUpRequestDto, Authority authority) {
    this.nickname = memberSignUpRequestDto.getNickname();
    this.password = memberSignUpRequestDto.getPassword();
    this.authority = authority;
  }


}
