package com.sparta.springhomework.controller;


import com.sparta.springhomework.domain.entity.Posting;
import com.sparta.springhomework.domain.enums.ErrorCode;
import com.sparta.springhomework.dto.request.CommentRequestDto;
import com.sparta.springhomework.dto.response.CommentResponseDto;
import com.sparta.springhomework.dto.response.ResponseDto;
import com.sparta.springhomework.exception.CustomException;
import com.sparta.springhomework.service.CommentService;
import com.sparta.springhomework.service.PostingService;
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
@RestController//필수
public class CommentController {

  private final CommentService commentService;
  private final PostingService postingService;

  //댓글작성
  @PostMapping("/api/auth/comment")
  public ResponseDto<CommentResponseDto> create(
      @RequestBody CommentRequestDto commentRequestDto) {
    CommentResponseDto commentResponseDto;
    try {

      Posting posting = postingService.findById(commentRequestDto.getPostId());
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

  //댓글 목록 조회
  @GetMapping("/api/comment/{id}") //post id 입력
  public ResponseDto<List<CommentResponseDto>> get(@PathVariable Long id) {
    List<CommentResponseDto> commentResponseDto;
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
  @Transactional
  @PutMapping("/api/auth/comment/{id}")
  public ResponseDto<CommentResponseDto> update(@PathVariable Long id,
      @RequestBody CommentRequestDto commentRequestDto) {
    CommentResponseDto commentResponseDto;

    try {
      Posting posting = postingService.findById(commentRequestDto.getPostId());
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
  @DeleteMapping("/api/auth/comment/{id}")
  public ResponseDto<String> deleteComment(@PathVariable Long id) {
    try {
      // 사용자 비교
      commentService.checkMember(id);

      // 삭제
      commentService.delete(id);
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
