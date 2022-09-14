package com.sparta.springhomework.domain.request;

import lombok.Getter;

@Getter
public class CommentCreateRequestDto {

  private Long postId;
  private String content;

}
