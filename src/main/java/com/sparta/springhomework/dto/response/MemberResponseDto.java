package com.sparta.springhomework.dto.response;

import com.sparta.springhomework.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {

  private Long id;
  private String nickname;

  public MemberResponseDto(Member member) {
    this.id = member.getId();
    this.nickname = member.getNickname();
  }
}
