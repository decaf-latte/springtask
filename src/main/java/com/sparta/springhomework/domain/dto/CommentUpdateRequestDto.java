package com.sparta.springhomework.domain.dto;


import lombok.Getter;

@Getter
public class CommentUpdateRequestDto {

  private Long postId;
  private String content;

}
