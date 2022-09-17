package com.sparta.springhomework.domain.request;

import lombok.Getter;

@Getter
public class CommentRequestDto {

  private Long postId;
  private String content;

}
