package com.sparta.springhomework.domain.dto;

import com.sparta.springhomework.domain.entity.Comment;
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
  private LocalDateTime createdAt;

  private MemberResponseDto member;

  private List<CommentResponseDto> comments = new ArrayList<>();

  public PostingResponseDto(Posting entity) {
    this.id = entity.getId();
    this.title = entity.getTitle();
    this.content = entity.getContent();
    this.author = entity.getAuthor();
    this.createdAt = entity.getCreatedAt();

    this.member = new MemberResponseDto(entity.getMember());

    // commentResponseDto를 받아온다
    for (Comment comment : entity.getComments()) {
      CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
      comments.add(commentResponseDto);
    }
  }
}
