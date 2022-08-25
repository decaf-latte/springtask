package com.sparta.springhomework.service.impl;

import com.sparta.springhomework.domain.dto.CommentRequestDto;
import com.sparta.springhomework.domain.dto.CommentResponseDto;
import com.sparta.springhomework.domain.entity.Comment;
import com.sparta.springhomework.domain.entity.Member;
import com.sparta.springhomework.domain.entity.Posting;
import com.sparta.springhomework.domain.entity.UserDetailsImpl;
import com.sparta.springhomework.domain.enums.ErrorCode;
import com.sparta.springhomework.exception.CustomException;
import com.sparta.springhomework.repository.CommentRepository;
import com.sparta.springhomework.repository.PostingRepository;
import com.sparta.springhomework.service.CommentService;
import java.util.Objects;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final PostingRepository postingRepository;


  //댓글 작성
  @Override
  @Transactional
  public CommentResponseDto create(CommentRequestDto commentRequestDto, Posting posting) {
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    Member member = userDetails.getMember();

    Comment comment = new Comment(commentRequestDto, posting, member);
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
  @Override //TODO 포스팅 업데이트보면서 수정
  public CommentResponseDto update(Long id, CommentRequestDto commentRequestDto, Posting posting) {
    Comment comment = commentRepository.findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    Member currentMember = userDetails.getMember();

    if (!Objects.equals(posting.getMember(), currentMember)) {
      throw new CustomException(ErrorCode.NOT_SAME_MEMBER);
    }

    return new CommentResponseDto(comment);
  }


  //댓글삭제
  @Override
  @Transactional //TODO 포스팅 업데이트보면서 수정
  public void delete(Long id) {
    Posting posting = postingRepository.findById(id).orElseThrow(
        () -> new EntityNotFoundException("해당 아이디가 존재하지 않습니다." + id)
    );
    Comment comment = commentRepository.findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    Member currentMember = userDetails.getMember();

    if (!Objects.equals(posting.getMember(), currentMember)) {
      throw new CustomException(ErrorCode.NOT_SAME_MEMBER);
    }
    commentRepository.deleteById(id);
  }
}
