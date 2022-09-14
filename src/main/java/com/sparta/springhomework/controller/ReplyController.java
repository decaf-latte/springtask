package com.sparta.springhomework.controller;


import com.sparta.springhomework.domain.entity.Comment;
import com.sparta.springhomework.domain.enums.ErrorCode;
import com.sparta.springhomework.domain.request.ReplyCreateRequestDto;
import com.sparta.springhomework.domain.request.ReplyUpdateRequestDto;
import com.sparta.springhomework.domain.response.ReplyResponseDto;
import com.sparta.springhomework.domain.response.ResponseDto;
import com.sparta.springhomework.exception.CustomException;
import com.sparta.springhomework.service.CommentService;
import com.sparta.springhomework.service.ReplyService;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j//에러메세지 로그에 찍기위한것
@RequiredArgsConstructor//필수
@RestController
public class ReplyController {

  private final CommentService commentService;

  private final ReplyService replyService;


  //대댓글 작성
  @PostMapping("/api/auth/reply")
  public ResponseDto<ReplyResponseDto> create(@RequestBody ReplyCreateRequestDto replyRequestDto) {
    ReplyResponseDto replyResponseDto;
    try {

      Comment comment = commentService.findById(replyRequestDto.getCommentId());
      replyResponseDto = replyService.create(replyRequestDto, comment);

    } catch (CustomException e) {
      log.error(e.getMessage());
      return new ResponseDto<>(null, e.getErrorCode());
    } catch (Exception e) {
      return new ResponseDto<>(null, ErrorCode.INVALID_ERROR);
    }
    return new ResponseDto<>(replyResponseDto);
  }

  //대댓글 목록 조회
  @GetMapping("/api/reply/{id}")//comment_id
  public ResponseDto<List<ReplyResponseDto>> get(@PathVariable Long id) {
    List<ReplyResponseDto> replyResponseDto;
    try {
      replyResponseDto = replyService.get(id);
    } catch (CustomException e) {
      log.error(e.getMessage());
      return new ResponseDto<>(null, e.getErrorCode());
    } catch (Exception e) {
      log.error(e.getMessage());
      return new ResponseDto<>(null, ErrorCode.INVALID_ERROR);
    }
    return new ResponseDto<>(replyResponseDto);
  }

  //대댓글 수정
  @Transactional
  @PutMapping("/api/auth/reply/{id}")//comment_id
  public ResponseDto<ReplyResponseDto> update(@PathVariable Long id,
      @RequestBody ReplyUpdateRequestDto replyUpdateRequestDto) {
    ReplyResponseDto replyResponseDto;
    try {
      Comment comment = commentService.findById(replyUpdateRequestDto.getCommentId());
      replyResponseDto = replyService.update(id, replyUpdateRequestDto, comment);
    } catch (CustomException e) {
      log.error(e.getMessage());
      return new ResponseDto<>(null, e.getErrorCode());
    } catch (Exception e) {
      log.error(e.getMessage());
      return new ResponseDto<>(null, ErrorCode.INVALID_ERROR);
    }
    return new ResponseDto<>(replyResponseDto);
  }

  //TODO:대댓글 삭제- 삭제 안된듯
  @DeleteMapping("/api/auth/reply/{id}")//reply_id
  public ResponseDto<String> delete(@PathVariable Long id) {
    try {
      replyService.delete(id);
    } catch (CustomException e) {
      log.error(e.getMessage());
      return new ResponseDto<>(null, e.getErrorCode());
    } catch (Exception e) {
      log.error(e.getMessage());
      return new ResponseDto<>(null, ErrorCode.INVALID_ERROR);
    }
    String data = "delete success";

    return new ResponseDto<>(data);
  }
}

