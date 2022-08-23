package com.sparta.springhomework.domain.dto;

import com.sparta.springhomework.domain.entity.Posting;
import com.sparta.springhomework.domain.entity.Timestamped;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;


@Getter
public class PostingResponseDto extends Timestamped {

  private final String title;
  private final String content;
  private final String author;
  private LocalDateTime createdAt;

  public PostingResponseDto(Posting entity) {
    this.title = entity.getTitle();
    this.content = entity.getContent();
    this.author = entity.getAuthor();
    this.createdAt = entity.getCreatedAt();
  }

  @Builder
  public PostingResponseDto(String title, String content, String author) {
    this.title = title;
    this.content = content;
    this.author = author;
  }
}
