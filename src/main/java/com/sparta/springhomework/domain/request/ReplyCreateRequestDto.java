package com.sparta.springhomework.domain.request;


import lombok.Getter;

@Getter
public class ReplyCreateRequestDto {

  private Long commentId;
  private String content;

}
