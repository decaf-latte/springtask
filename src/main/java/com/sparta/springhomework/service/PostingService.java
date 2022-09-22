package com.sparta.springhomework.service;

import com.sparta.springhomework.domain.entity.Member;
import com.sparta.springhomework.domain.entity.Posting;
import com.sparta.springhomework.dto.request.PostingRequestDto;
import com.sparta.springhomework.dto.response.PostingDetailResponseDto;
import com.sparta.springhomework.dto.response.PostingListResponseDto;
import com.sparta.springhomework.dto.response.PostingResponseDto;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface PostingService {

  List<PostingListResponseDto> getAll();

  //게시글 작성
  PostingResponseDto create(PostingRequestDto postingRequestDto, Member member,
      MultipartFile image);

  PostingDetailResponseDto getPosting(Long id);

  //게시글만 수정
  PostingResponseDto update(Long id, PostingRequestDto postingRequestDto);

  //게시글 사진 수정
  PostingResponseDto updateImage(Long id, MultipartFile image);

  //게시글 사진 삭제
  @Transactional
  void deleteImage(Long id);

  void delete(Long id);

  PostingDetailResponseDto getById(Long id);

  Posting findById(Long id);

}
