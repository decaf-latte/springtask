package com.sparta.springhomework.domain.entity;


import com.sparta.springhomework.domain.dto.CommentRequestDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(columnDefinition = "TEXT") // DB의 컬럼 속성을 TEXT로 바꿔서 기본 varchar 보다 많은 데이터를 저장
  private String content;

  private String author;

  //연관관계매핑
  @ManyToOne
  @JoinColumn(name = "posting_id")
  private Posting posting;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  public Comment(CommentRequestDto commentRequestDto, Posting posting, Member member) {
    this.author = posting.getAuthor();
    this.content = commentRequestDto.getContent();
    this.posting = posting;
    this.member = member;
  }
  
//  API 명세서 확인하면서 출력값 맞는지 확인하기


}
