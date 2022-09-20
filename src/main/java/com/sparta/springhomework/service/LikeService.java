package com.sparta.springhomework.service;

import com.sparta.springhomework.dto.response.ResponseDto;
import javax.servlet.http.HttpServletRequest;

public interface LikeService {

  ResponseDto<String> togglePostLike(Long postId, HttpServletRequest request);
}
