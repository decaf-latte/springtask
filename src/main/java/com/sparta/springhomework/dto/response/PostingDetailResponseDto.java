package com.sparta.springhomework.dto.response;

import com.sparta.springhomework.domain.entity.Comment;
import com.sparta.springhomework.domain.entity.Posting;
import com.sparta.springhomework.domain.entity.Timestamped;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class PostingDetailResponseDto extends Timestamped {

  private final Long id;
  private final String title;
  private final String content;

  private Long likes;

  private final LocalDateTime createdAt;

  private final LocalDateTime modifiedAt;

  private final MemberResponseDto member;

  private final List<CommentResponseDto> comments = new ArrayList<>();

  private final List<ReplyResponseDto> replies = new ArrayList<>();

  public PostingDetailResponseDto(Posting posting) {
    this.createdAt = posting.getCreatedAt();
    this.modifiedAt = posting.getModifiedAt();

    this.id = posting.getId();
    this.title = posting.getTitle();
    this.content = posting.getContent();
    this.member = new MemberResponseDto(posting.getMember());

    this.likes = posting.getLikes();

    // commentResponseDto를 받아온다
    for (Comment comment : posting.getComments()) {
      CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
      comments.add(commentResponseDto);
    }
  }
}