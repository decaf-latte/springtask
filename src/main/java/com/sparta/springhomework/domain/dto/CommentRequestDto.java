package com.sparta.springhomework.domain.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {

  private Long postId;
  private String content;

}
