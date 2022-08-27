package com.sparta.springhomework.domain.dto;

import com.sparta.springhomework.domain.entity.Comment;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentResponseDto {

  private Long id;

  private Long postId;
  private String author;
  private String content;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

  private MemberResponseDto member;

  public CommentResponseDto(Comment comment) {
    this.id = comment.getId();
    this.postId = comment.getPosting().getId();
    this.author = comment.getMember().getNickname();
    this.content = comment.getContent();
    this.createdAt = comment.getCreatedAt();
    this.modifiedAt = comment.getModifiedAt();

  }
}
