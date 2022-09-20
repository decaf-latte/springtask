package com.sparta.springhomework.controller;

import com.sparta.springhomework.dto.response.ResponseDto;
import com.sparta.springhomework.service.LikeService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

  private final LikeService likeService;

  //게시글 좋아요
  @PostMapping("/api/auth/like/post/{postId}")
  public ResponseDto<?> togglePostLike(@PathVariable Long postId, HttpServletRequest request) {
    return likeService.togglePostLike(postId, request);
  }


}
