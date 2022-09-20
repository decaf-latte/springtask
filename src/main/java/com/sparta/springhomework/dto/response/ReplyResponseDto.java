package com.sparta.springhomework.dto.response;


import com.sparta.springhomework.domain.entity.Reply;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ReplyResponseDto {

  private final Long id;

  private final Long commentId;

  private final String author;

  private final String content;

  private final LocalDateTime createdAt;

  private final LocalDateTime modifiedAt;


  public ReplyResponseDto(Reply reply) {
    this.id = reply.getId();
    this.commentId = reply.getComment().getId();
    this.author = reply.getMember().getNickname();
    this.content = reply.getContent();
    this.createdAt = reply.getCreatedAt();
    this.modifiedAt = reply.getModifiedAt();

  }
}
