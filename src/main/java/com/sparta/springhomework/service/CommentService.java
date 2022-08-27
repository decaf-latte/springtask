package com.sparta.springhomework.service;

import com.sparta.springhomework.domain.dto.CommentRequestDto;
import com.sparta.springhomework.domain.dto.CommentResponseDto;
import com.sparta.springhomework.domain.entity.Posting;
import java.util.List;

public interface CommentService {

  CommentResponseDto create(CommentRequestDto commentRequestDto,
      Posting posting);

  List<CommentResponseDto> get(Long id);

  CommentResponseDto update(Long id, CommentRequestDto commentRequestDto, Posting posting);

  void delete(Long id);

}
