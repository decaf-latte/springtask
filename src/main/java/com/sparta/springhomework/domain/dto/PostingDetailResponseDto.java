package com.sparta.springhomework.domain.dto;

import com.sparta.springhomework.domain.entity.Comment;
import com.sparta.springhomework.domain.entity.Posting;
import com.sparta.springhomework.domain.entity.Timestamped;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class PostingDetailResponseDto extends Timestamped {

  private Long id;
  private String title;
  private String content;

  private LocalDateTime createdAt;

  private LocalDateTime modifiedAt;

  private MemberResponseDto member;

  private List<CommentResponseDto> comments = new ArrayList<>();

  private List<ReplyResponseDto> replies = new ArrayList<>();

  public PostingDetailResponseDto(Posting posting) {
    this.createdAt = posting.getCreatedAt();
    this.modifiedAt = posting.getModifiedAt();

    this.id = posting.getId();
    this.title = posting.getTitle();
    this.content = posting.getContent();
    this.member = new MemberResponseDto(posting.getMember());

    // commentResponseDto를 받아온다
    for (Comment comment : posting.getComments()) {
      CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
      comments.add(commentResponseDto);
    }
  }
}