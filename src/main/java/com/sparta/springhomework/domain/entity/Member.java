package com.sparta.springhomework.domain.entity;


import com.sparta.springhomework.domain.enums.Authority;
import com.sparta.springhomework.domain.request.MemberSignUpRequestDto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "member")
public class Member extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String nickname;

  @Column(nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  private Authority authority;

  @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Posting> postings = new ArrayList<>();

  @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Comment> comments = new ArrayList<>();

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
