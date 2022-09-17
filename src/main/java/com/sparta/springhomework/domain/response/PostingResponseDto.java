package com.sparta.springhomework.domain.response;


import com.sparta.springhomework.domain.entity.Posting;
import com.sparta.springhomework.domain.entity.Timestamped;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter

public class PostingResponseDto extends Timestamped {

  private final Long id;
  private final String title;
  private final String content;
  private final String author;

  private final LocalDateTime createdAt;

  private final LocalDateTime modifiedAt;

  private final MemberResponseDto member;

  private final List<CommentResponseDto> comments = new ArrayList<>();


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
