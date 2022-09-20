package com.sparta.springhomework.service;

import com.sparta.springhomework.domain.entity.Comment;
import com.sparta.springhomework.dto.request.ReplyRequestDto;
import com.sparta.springhomework.dto.response.ReplyResponseDto;
import java.util.List;

public interface ReplyService {

  ReplyResponseDto create(ReplyRequestDto replyRequestDto, Comment comment);

  List<ReplyResponseDto> get(Long id);

  ReplyResponseDto update(Long id, ReplyRequestDto replyRequestDto, Comment comment);

  void delete(Long id);

}
