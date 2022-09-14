package com.sparta.springhomework.domain.response;


import com.sparta.springhomework.domain.entity.Reply;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ReplyResponseDto {

  private Long id;

  private Long commentId;

  private String author;

  private String content;

  private LocalDateTime createdAt;

  private LocalDateTime modifiedAt;


  public ReplyResponseDto(Reply reply) {
    this.id = reply.getId();
    this.commentId = reply.getComment().getId();
    this.author = reply.getMember().getNickname();
    this.content = reply.getContent();
    this.createdAt = reply.getCreatedAt();
    this.modifiedAt = reply.getModifiedAt();

  }
}
