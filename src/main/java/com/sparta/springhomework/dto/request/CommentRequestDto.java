package com.sparta.springhomework.dto.request;

import lombok.Getter;

@Getter
public class CommentRequestDto {

  private Long postId;
  private String content;

}
