package com.sparta.springhomework.domain.dto;

import com.sparta.springhomework.domain.entity.Member;
import java.time.LocalDateTime;
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
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

  public MemberResponseDto(Member member) {
    this.id = member.getId();
    this.nickname = member.getNickname();
    this.createdAt = member.getCreatedAt();
    this.modifiedAt = member.getModifiedAt();
  }
}
