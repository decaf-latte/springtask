package com.sparta.springhomework.domain.response;


import com.sparta.springhomework.domain.entity.Posting;
import com.sparta.springhomework.domain.entity.Timestamped;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter

public class PostingResponseDto extends Timestamped {

  private Long id;
  private String title;
  private String content;
  private String author;

  private LocalDateTime createdAt;

  private LocalDateTime modifiedAt;

  private MemberResponseDto member;

  private List<CommentResponseDto> comments = new ArrayList<>();


  public PostingResponseDto(Posting posting) {
    this.id = posting.getId();
    this.title = posting.getTitle();
    this.content = posting.getContent();
    this.author = posting.getMember().getNickname();
    this.member = new MemberResponseDto(posting.getMember());
    this.createdAt = posting.getCreatedAt();
    this.modifiedAt = posting.getModifiedAt();
  }
}
