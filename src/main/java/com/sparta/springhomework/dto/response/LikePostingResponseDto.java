package com.sparta.springhomework.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikePostingResponseDto {

  private Long id;
  private String title;
  private String content;
  private String author;
  private Long likes;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
  private List<CommentResponseDto> commentResponseDtoList;


}
