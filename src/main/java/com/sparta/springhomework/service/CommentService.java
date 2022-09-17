package com.sparta.springhomework.service;

import com.sparta.springhomework.domain.entity.Comment;
import com.sparta.springhomework.domain.entity.Posting;
import com.sparta.springhomework.domain.request.CommentRequestDto;
import com.sparta.springhomework.domain.response.CommentResponseDto;
import java.util.List;

public interface CommentService {

  CommentResponseDto create(CommentRequestDto commentRequestDto,
      Posting posting);

  List<CommentResponseDto> get(Long id);

  CommentResponseDto update(Long id, CommentRequestDto commentRequestDto,
      Posting posting);

  void delete(Long id);

  Comment findById(Long id);

  void checkMember(Long id);
}
