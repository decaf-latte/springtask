package com.sparta.springhomework.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class TokenStore extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  // TEXT -> DB의 데이터 타입으로 varchar(String) 보다 더 큰 데이터를 저장할 수 있다.
  @Column(name = "refresh_token", columnDefinition = "TEXT", nullable = false)
  private String refreshToken;

  public TokenStore(Member member, String refreshToken) {
    this.member = member;
    this.refreshToken = refreshToken;
  }
}
