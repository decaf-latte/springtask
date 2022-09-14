package com.sparta.springhomework.service.impl;

import com.sparta.springhomework.domain.entity.Comment;
import com.sparta.springhomework.domain.entity.Member;
import com.sparta.springhomework.domain.entity.Posting;
import com.sparta.springhomework.domain.entity.UserDetailsImpl;
import com.sparta.springhomework.domain.enums.ErrorCode;
import com.sparta.springhomework.domain.request.CommentCreateRequestDto;
import com.sparta.springhomework.domain.request.CommentUpdateRequestDto;
import com.sparta.springhomework.domain.response.CommentResponseDto;
import com.sparta.springhomework.exception.CustomException;
import com.sparta.springhomework.repository.CommentRepository;
import com.sparta.springhomework.repository.PostingRepository;
import com.sparta.springhomework.service.CommentService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final PostingRepository postingRepository;

  //댓글 작성
  @Override
  @Transactional
  public CommentResponseDto create(CommentCreateRequestDto commentRequestDto,
      Posting posting) {
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    Member member = userDetails.getMember();

    Comment comment = new Comment(commentRequestDto, posting, member);
    comment = commentRepository.save(comment);
    return new CommentResponseDto(comment);
  }

  //댓글 목록 조회
  @Override
  public List<CommentResponseDto> get(Long id) {
    Posting posting = postingRepository.findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
//TODO
    List<Comment> comments = posting.getComments();

    List<CommentResponseDto> commentResponseDtos = new ArrayList<>();

    // foreach문 : for을 돌릴때 int i = 0; i < size; i++  << 이거 선언해서 index번호의 데이터를 가져오는게 아니라 요소를 순서대로 꺼내오는 문법
    for (Comment comment : comments) {
      CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
      commentResponseDtos.add(commentResponseDto);
    }
    return commentResponseDtos;
  }


  //댓글 수정
  @Override
  @Transactional
  public CommentResponseDto update(Long id, CommentUpdateRequestDto commentUpdateRequestDto,
      Posting posting) {
    Comment comment = commentRepository.findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    Member currentMember = userDetails.getMember();

    if (!Objects.equals(posting.getMember().getId(), currentMember.getId())) {
      throw new CustomException(ErrorCode.NOT_SAME_MEMBER);
    }

    comment.update(commentUpdateRequestDto, posting);
    return new CommentResponseDto(comment);
  }

  //댓글삭제
  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void delete(Long id) {
//    Comment comment = commentRepository.findById(id)
//        .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
//
//    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
//        .getAuthentication().getPrincipal();
//
//    Member currentMember = userDetails.getMember();
//
//    if (!Objects.equals(comment.getMember().getId(), currentMember.getId())) {
//      throw new CustomException(ErrorCode.NOT_SAME_MEMBER);
//    }

    commentRepository.deleteById(id);
  }

  public Comment findById(Long id) {
    Comment comment = commentRepository.findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
    return comment;
  }

  @Override
  @Transactional(readOnly = true)
  public void checkMember(Long id) {

    Comment comment = commentRepository.findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    Member currentMember = userDetails.getMember();

    if (!Objects.equals(comment.getMember().getId(), currentMember.getId())) {
      throw new CustomException(ErrorCode.NOT_SAME_MEMBER);
    }
  }
}
