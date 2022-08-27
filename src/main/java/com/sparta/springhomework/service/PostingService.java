package com.sparta.springhomework.service;

import com.sparta.springhomework.domain.dto.PostingDetailResponseDto;
import com.sparta.springhomework.domain.dto.PostingListResponseDto;
import com.sparta.springhomework.domain.dto.PostingRequestDto;
import com.sparta.springhomework.domain.dto.PostingResponseDto;
import com.sparta.springhomework.domain.entity.Member;
import com.sparta.springhomework.domain.entity.Posting;
import java.util.List;

public interface PostingService {

  List<PostingListResponseDto> getAll();

  PostingResponseDto create(PostingRequestDto postingRequestDto, Member member);

  PostingDetailResponseDto getPosting(Long id);

  PostingResponseDto update(Long id, PostingRequestDto postingRequestDto);

  void delete(Long id);

  PostingDetailResponseDto getById(Long id);

  Posting findById(Long id);

}
