package com.sparta.springhomework.domain.dto;

import lombok.Getter;

@Getter
public class PostingRequestDto {

  private String title;
  private String content;
//    private String author;
//    private String password;

  @Getter
  public static class PostingPasswordDto {

    private String password;

  }

}
