package com.sparta.springhomework.domain.dto;

import com.sparta.springhomework.domain.entity.Comment;
import com.sparta.springhomework.domain.entity.Reply;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class CommentResponseDto {

  private Long id;

  private Long postId;
  private String author;
  private String content;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

  private List<ReplyResponseDto> replies = new ArrayList<>();


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
