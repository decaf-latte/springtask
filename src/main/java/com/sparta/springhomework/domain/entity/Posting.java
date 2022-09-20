package com.sparta.springhomework.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.springhomework.domain.request.PostingRequestDto;
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
//@Access(AccessType.PROPERTY)
public class Posting extends Timestamped {

  // ID가 자동으로 생성 및 증가합니다.
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long id;

  // 반드시 값을 가지도록 합니다.
  @Column(nullable = false)
  private String title;

  @JsonIgnore
  @Column(nullable = false)
  private String content;

  @Column(nullable = false)
  private Long likes;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  // comment와 양방향 매핑을 해준다.
  // 조회 시 즉시 로딩(EAGER) 을 통해 한 번에 모든 comment를 가져온다.
  // cascade -> 삭제 업데이트 등 상태 변경 시 연관관계에 있는 데이터에도 변경 전파 옵션 (나중에 프로젝트 할 때 옵션 찾아본다)
  // / 삭제 시 comment도 삭제 되게 조치
  // OneToMany의 기본 fetch type -> LAZY
  // ManyToOne -> EAGER
  @OneToMany(mappedBy = "posting", cascade = CascadeType.REMOVE)//, orphanRemoval = true)
  private List<Comment> comments = new ArrayList<>();

  @JsonIgnore
  @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
  private List<LikePost> likePost;

  //게시글 작성시
  public Posting(PostingRequestDto postingRequestDto, Member member) {
    this.title = postingRequestDto.getTitle();
    this.content = postingRequestDto.getContent();
    this.member = member;
  }

  //게시글 수정시
  public void update(PostingRequestDto postingRequestDto) {
    this.title = postingRequestDto.getTitle();
    this.content = postingRequestDto.getContent();
  }

  //게시글 좋아요시
  public void updateLikes(Long likes) {
    this.likes = likes;
  }

}
