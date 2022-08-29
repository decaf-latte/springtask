package com.sparta.springhomework.domain.dto;


import lombok.Getter;

@Getter
public class ReplyCreateRequestDto {

  private Long commentId;
  private String content;

}
