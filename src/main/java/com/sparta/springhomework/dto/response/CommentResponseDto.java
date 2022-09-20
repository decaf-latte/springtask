package com.sparta.springhomework.dto.response;

import com.sparta.springhomework.domain.entity.Comment;
import com.sparta.springhomework.domain.entity.Reply;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class CommentResponseDto {

  private final Long id;

  private final Long postId;
  private final String author;
  private final String content;
  private final LocalDateTime createdAt;
  private final LocalDateTime modifiedAt;

  private final List<ReplyResponseDto> replies = new ArrayList<>();


  public CommentResponseDto(Comment comment) {
    this.id = comment.getId();
    this.postId = comment.getPosting().getId();
    this.author = comment.getMember().getNickname();
    this.content = comment.getContent();
    this.createdAt = comment.getCreatedAt();
    this.modifiedAt = comment.getModifiedAt();

    for (Reply reply : comment.getReplies()) {
      ReplyResponseDto replyResponseDto = new ReplyResponseDto(reply);
      replies.add(replyResponseDto);
    }

  }
}
