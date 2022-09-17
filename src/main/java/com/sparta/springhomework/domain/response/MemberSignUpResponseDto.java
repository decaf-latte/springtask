package com.sparta.springhomework.domain.response;

import com.sparta.springhomework.domain.entity.Member;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class MemberSignUpResponseDto {

  private final Long id;
  private final String nickname;
  private final LocalDateTime createdAt;
  private final LocalDateTime modifiedAt;

  public MemberSignUpResponseDto(Member member) {
    this.id = member.getId();
    this.nickname = member.getNickname();
    this.modifiedAt = member.getModifiedAt();
    this.createdAt = member.getCreatedAt();

  }

}
