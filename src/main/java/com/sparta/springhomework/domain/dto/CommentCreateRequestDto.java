package com.sparta.springhomework.domain.dto;

import lombok.Getter;

@Getter
public class CommentCreateRequestDto {

  private Long postId;
  private String content;

}
