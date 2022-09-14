package com.sparta.springhomework.domain.request;


import lombok.Getter;

@Getter
public class CommentUpdateRequestDto {

  private Long postId;
  private String content;

}
