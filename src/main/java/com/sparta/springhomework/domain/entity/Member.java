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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode
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

  //  @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//  private Set<Posting> postings = new HashSet<>();
//
//  @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//  private Set<Comment> comments = new HashSet<>();

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

  //해쉬코드 값 비교

//  @Override
//  public boolean equals(Object o) {
//    if (this == o) {
//      return true;
//    }
//    if (o == null || getClass() != o.getClass()) {
//      return false;
//    }
//    Member member = (Member) o;
//    return Objects.equals(id, member.id) && Objects.equals(nickname,
//        member.nickname) && Objects.equals(password, member.password)
//        && authority == member.authority && Objects.equals(postings, member.postings)
//        && Objects.equals(comments, member.comments);
//  }
//
//  @Override
//  public int hashCode() {
//    return Objects.hash(id, nickname, password, authority, postings, comments);
//  }
}
