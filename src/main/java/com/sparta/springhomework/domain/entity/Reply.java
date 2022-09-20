package com.sparta.springhomework.domain.entity;


import com.sparta.springhomework.dto.request.ReplyRequestDto;
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
public class Reply extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "TEXT") // DB의 컬럼 속성을 TEXT로 바꿔서 기본 varchar 보다 많은 데이터를 저장
  private String content;

  private String author;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "comment_id")
  private Comment comment;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "posting_id")
  private Posting posting;


  public Reply(ReplyRequestDto replyRequestDto, Comment comment, Member member) {
    this.author = member.getNickname();
    this.content = replyRequestDto.getContent();
    this.comment = comment;
    this.member = member;
  }

  public void update(ReplyRequestDto replyRequestDto, Comment comment) {
    this.content = replyRequestDto.getContent();
  }

}
