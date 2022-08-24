package com.sparta.springhomework.service.impl;

import com.sparta.springhomework.domain.dto.CommentRequestDto;
import com.sparta.springhomework.domain.dto.CommentResponseDto;
import com.sparta.springhomework.domain.entity.Comment;
import com.sparta.springhomework.domain.entity.Posting;
import com.sparta.springhomework.domain.enums.ErrorCode;
import com.sparta.springhomework.exception.CustomException;
import com.sparta.springhomework.repository.CommentRepository;
import com.sparta.springhomework.service.CommentService;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;


  //댓글 작성
  @Override
  @Transactional
  public CommentResponseDto create(CommentRequestDto commentRequestDto, Posting posting) {
    Comment comment = new Comment(commentRequestDto, posting);
    comment = commentRepository.save(comment);
    return new CommentResponseDto(comment);
  }

  //댓글 조회
  @Override
  public CommentResponseDto get(Long id) {
    Comment comment = commentRepository.findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
    return new CommentResponseDto(comment);
  }

  //댓글 수정
  @Override
  public CommentResponseDto update(Long id, CommentRequestDto commentRequestDto, Posting posting) {
    Comment comment = commentRepository.findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

    return new CommentResponseDto(comment);
  }


  //댓글삭제
  @Override
  @Transactional
  public void delete(Long id) {
    commentRepository.deleteById(id);
  }
}
