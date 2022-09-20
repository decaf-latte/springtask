package com.sparta.springhomework.dto.response;

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
public class MemberLogInResponseDto {

  private Long id;
  private String nickname;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;


  public MemberLogInResponseDto(Member member) {
    this.id = member.getId();
    this.nickname = member.getNickname();
    this.modifiedAt = member.getModifiedAt();
    this.createdAt = member.getCreatedAt();

  }
}
