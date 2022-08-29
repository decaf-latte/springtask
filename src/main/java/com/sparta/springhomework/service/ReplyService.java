package com.sparta.springhomework.service;

import com.sparta.springhomework.domain.dto.ReplyCreateRequestDto;
import com.sparta.springhomework.domain.dto.ReplyResponseDto;
import com.sparta.springhomework.domain.dto.ReplyUpdateRequestDto;
import com.sparta.springhomework.domain.entity.Comment;
import java.util.List;

public interface ReplyService {

  ReplyResponseDto create(ReplyCreateRequestDto replyRequestDto, Comment comment);

  List<ReplyResponseDto> get(Long id);

  ReplyResponseDto update(Long id, ReplyUpdateRequestDto replyUpdateRequestDto, Comment comment);

  void delete(Long id);

}
