package com.sparta.springhomework.dto.response;

import com.sparta.springhomework.domain.entity.Comment;
import com.sparta.springhomework.domain.entity.Posting;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
//@AllArgsConstructor
public class PostingListResponseDto {

  private Long id;
  private String title;
  private String content;

  private String postImg;
  private Long likes;
  private MemberResponseDto member;

  private final List<CommentResponseDto> comments = new ArrayList<>();

  private final List<ReplyResponseDto> replies = new ArrayList<>();

  public PostingListResponseDto(Posting posting) {
    this.id = posting.getId();
    this.title = posting.getTitle();
    this.content = posting.getContent();
    this.postImg = posting.getPostImg();
    this.likes = posting.getLikes();
    this.member = new MemberResponseDto(posting.getMember());

    // commentResponseDto를 받아온다
    for (Comment comment : posting.getComments()) {
      CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
      comments.add(commentResponseDto);
    }
  }
}
