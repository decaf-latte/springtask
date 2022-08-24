package com.sparta.springhomework.controller;


import com.sparta.springhomework.domain.dto.CommentRequestDto;
import com.sparta.springhomework.domain.dto.CommentResponseDto;
import com.sparta.springhomework.domain.dto.ResponseDto;
import com.sparta.springhomework.domain.entity.Posting;
import com.sparta.springhomework.domain.enums.ErrorCode;
import com.sparta.springhomework.exception.CustomException;
import com.sparta.springhomework.service.CommentService;
import com.sparta.springhomework.service.PostingService;
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
@RestController//필수
public class CommentController {

  private final CommentService commentService;
  private final PostingService postingService;

  //댓글작성
  @PostMapping("/api/auth/comment")
  public ResponseDto<CommentResponseDto> create(@RequestBody CommentRequestDto commentRequestDto) {
    CommentResponseDto commentResponseDto;
    try {
      Posting posting = postingService.get(commentRequestDto.getPostId());
      commentResponseDto = commentService.create(commentRequestDto, posting);
    } catch (CustomException e) {
      log.error(e.getMessage());
      return new ResponseDto<>(null, e.getErrorCode());
    } catch (Exception e) {
      log.error(e.getMessage());
      return new ResponseDto<>(null, ErrorCode.INVALID_ERROR);
    }
    return new ResponseDto<>(commentResponseDto);
  }

  //댓글조회
  @GetMapping("/api/comment/{id}")
  public ResponseDto<CommentResponseDto> get(@PathVariable Long id) {
    CommentResponseDto commentResponseDto;
    try {
      commentResponseDto = commentService.get(id);
    } catch (CustomException e) {
      log.error(e.getMessage());
      return new ResponseDto<>(null, e.getErrorCode());
    } catch (Exception e) {
      log.error(e.getMessage());
      return new ResponseDto<>(null, ErrorCode.INVALID_ERROR);
    }
    return new ResponseDto<>(commentResponseDto);
  }

  //댓글수정
  @PutMapping("/api/comment/{id}")
  public ResponseDto<CommentResponseDto> update(@PathVariable Long id,
      @RequestBody CommentRequestDto commentRequestDto) {
    CommentResponseDto commentResponseDto;
    try {
      Posting posting = postingService.get(commentRequestDto.getPostId());
      commentResponseDto = commentService.update(id, commentRequestDto, posting);
    } catch (CustomException e) {
      log.error(e.getMessage());
      return new ResponseDto<>(null, e.getErrorCode());
    } catch (Exception e) {
      log.error(e.getMessage());
      return new ResponseDto<>(null, ErrorCode.INVALID_ERROR);
    }

    return new ResponseDto<>(commentResponseDto);
  }


  //댓글삭제
  @DeleteMapping("/api/comment/{id}")
  public ResponseDto<Boolean> delete(@PathVariable Long id) {
    try {
      commentService.delete(id);
    } catch (CustomException e) {
      log.error(e.getMessage());
      return new ResponseDto<>(null, e.getErrorCode());
    } catch (Exception e) {
      log.error(e.getMessage());
      return new ResponseDto<>(null, ErrorCode.INVALID_ERROR);
    }

    return new ResponseDto<>(true);
  }

}
