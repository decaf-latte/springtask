package com.sparta.springhomework.service.impl;

import com.sparta.springhomework.domain.entity.Comment;
import com.sparta.springhomework.domain.entity.Member;
import com.sparta.springhomework.domain.entity.Reply;
import com.sparta.springhomework.domain.entity.UserDetailsImpl;
import com.sparta.springhomework.domain.enums.ErrorCode;
import com.sparta.springhomework.domain.request.ReplyRequestDto;
import com.sparta.springhomework.domain.response.ReplyResponseDto;
import com.sparta.springhomework.exception.CustomException;
import com.sparta.springhomework.repository.CommentRepository;
import com.sparta.springhomework.repository.PostingRepository;
import com.sparta.springhomework.repository.ReplyRepository;
import com.sparta.springhomework.service.ReplyService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

  private final CommentRepository commentRepository;
  private final PostingRepository postingRepository;
  private final ReplyRepository replyRepository;

  //대댓글 작성
  @Override
  @Transactional
  public ReplyResponseDto create(ReplyRequestDto replyRequestDto, Comment comment) {
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    Member member = userDetails.getMember();

    Reply reply = new Reply(replyRequestDto, comment, member);
    reply = replyRepository.save(reply);
    return new ReplyResponseDto(reply);
  }

  //대댓글 목록 조회
  @Override
  public List<ReplyResponseDto> get(Long id) {
    Comment comment = commentRepository.findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
//TODO
    List<Reply> replies = comment.getReplies();

    List<ReplyResponseDto> replyResponseDtos = new ArrayList<>();

    for (Reply reply : replies) {
      ReplyResponseDto replyResponseDto = new ReplyResponseDto(reply);
      replyResponseDtos.add(replyResponseDto);
    }
    return replyResponseDtos;
  }

  //대댓글 수정
  @Override
  public ReplyResponseDto update(Long id, ReplyRequestDto replyRequestDto,
      Comment comment) {
    Reply reply = replyRepository.findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    Member currentMember = userDetails.getMember();

    if (!Objects.equals(comment.getMember().getId(), currentMember.getId())) {
      throw new CustomException(ErrorCode.NOT_SAME_MEMBER);
    }

    reply.update(replyRequestDto, comment);
    return new ReplyResponseDto(reply);
  }


  //대댓글 삭제
  @Override
  @Transactional
  public void delete(Long id) {
    Reply reply = replyRepository.findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    Member currentMember = userDetails.getMember();

    if (!Objects.equals(reply.getMember().getId(), currentMember.getId())) {
      throw new CustomException(ErrorCode.NOT_SAME_MEMBER);
    }
    replyRepository.deleteById(id);
  }
}
