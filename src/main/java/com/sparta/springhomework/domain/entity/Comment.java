package com.sparta.springhomework.domain.entity;


import com.sparta.springhomework.domain.request.CommentRequestDto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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

  //TODO
  @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE) //orphanRemoval = true)
  private List<Reply> replies = new ArrayList<>();

  public Comment(CommentRequestDto commentRequestDto, Posting posting, Member member) {
    this.author = member.getNickname();
    this.content = commentRequestDto.getContent();
    this.posting = posting;
    this.member = member;
  }

  // update이므로 생성자가 아닌 일반 메소드를 이용한다.
  public void update(CommentRequestDto commentRequestDto, Posting posting) {
    this.content = commentRequestDto.getContent();
    this.posting = posting;
  }


}
