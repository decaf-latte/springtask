package com.sparta.springhomework.service.impl;

import com.sparta.springhomework.domain.entity.Member;
import com.sparta.springhomework.domain.entity.Posting;
import com.sparta.springhomework.domain.entity.UserDetailsImpl;
import com.sparta.springhomework.domain.enums.ErrorCode;
import com.sparta.springhomework.dto.request.PostingRequestDto;
import com.sparta.springhomework.dto.response.PostingDetailResponseDto;
import com.sparta.springhomework.dto.response.PostingListResponseDto;
import com.sparta.springhomework.dto.response.PostingResponseDto;
import com.sparta.springhomework.exception.CustomException;
import com.sparta.springhomework.repository.PostingRepository;
import com.sparta.springhomework.service.PostingService;
import com.sparta.springhomework.util.S3Upload;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostingServiceImpl implements PostingService {

  private final PostingRepository postingRepository;

  private final S3Upload s3Upload;

  //전체 조회 (목록)
  public List<PostingListResponseDto> getAll() {
    return postingRepository.findAllByOrderByModifiedAtDesc();
  }

  //게시글 작성
  @Override
  public PostingResponseDto create(PostingRequestDto postingRequestDto, Member member,
      MultipartFile image) {

    String postImg = null;

    if (image != null) {
      try {
        postImg = s3Upload.upload(image, "images");
        System.out.println(postImg);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    Posting posting = new Posting(postingRequestDto, member, postImg);

    posting = postingRepository.save(posting);
    return new PostingResponseDto(posting);
  }

  //TODO:게시글 상세조회
  @Override
  public PostingDetailResponseDto getPosting(Long id) {
    Posting posting = postingRepository.findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
    return new PostingDetailResponseDto(posting);
  }

  //게시글 수정
  @Override
  @Transactional
  public PostingResponseDto update(Long id, PostingRequestDto postingRequestDto) {
    Posting posting = postingRepository.findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    Member currentMember = userDetails.getMember();

    if (!Objects.equals(posting.getMember().getId(), currentMember.getId())) {
      throw new CustomException(ErrorCode.NOT_SAME_MEMBER);
    }

    posting.update(postingRequestDto);
    return new PostingResponseDto(posting);
  }

  //게시글 삭제
  @Override
  @Transactional
  public void delete(Long id) {
    Posting posting = postingRepository.findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    Member currentMember = userDetails.getMember();

    if (!Objects.equals(posting.getMember().getId(), currentMember.getId())) {
      throw new CustomException(ErrorCode.NOT_SAME_MEMBER);
    }
    postingRepository.deleteById(id);
  }


  @Override
  public PostingDetailResponseDto getById(Long id) {
    Posting posting = postingRepository.findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
    return new PostingDetailResponseDto(posting);
  }


  @Override
  public Posting findById(Long id) {
    return postingRepository.findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
  }
}
