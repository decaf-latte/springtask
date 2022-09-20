package com.sparta.springhomework.controller;

import com.sparta.springhomework.domain.entity.Member;
import com.sparta.springhomework.domain.entity.UserDetailsImpl;
import com.sparta.springhomework.domain.enums.ErrorCode;
import com.sparta.springhomework.dto.request.PostingRequestDto;
import com.sparta.springhomework.dto.response.PostingDetailResponseDto;
import com.sparta.springhomework.dto.response.PostingListResponseDto;
import com.sparta.springhomework.dto.response.PostingResponseDto;
import com.sparta.springhomework.dto.response.ResponseDto;
import com.sparta.springhomework.exception.CustomException;
import com.sparta.springhomework.service.PostingService;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
public class PostingController {


  private final PostingService postingService;

  //전체조회
  @GetMapping("/api/post")
  public ResponseDto<List<PostingListResponseDto>> getAll() {
    List<PostingListResponseDto> data;

    try {
      data = postingService.getAll();
    } catch (Exception e) {
      return new ResponseDto<>(null, ErrorCode.INVALID_ERROR);
    }
    return new ResponseDto<>(data);
  }

  //게시글 작성
  @PostMapping("/api/auth/post")
  public ResponseDto<PostingResponseDto> create(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody PostingRequestDto postingRequestDto) {

    PostingResponseDto postingResponseDto;

    try {
      Member member = userDetails.getMember();
      postingResponseDto = postingService.create(postingRequestDto, member);

    } catch (Exception e) {
      log.error("error: ", e);
      return new ResponseDto<>(null, ErrorCode.INVALID_ERROR);
    }
    return new ResponseDto<>(postingResponseDto);

  }

  //게시글 조회
  @GetMapping("/api/post/{id}")
  public ResponseDto<PostingDetailResponseDto> getPosting(@PathVariable Long id) {
    PostingDetailResponseDto postingDetailResponseDto;
    try {
      postingDetailResponseDto = postingService.getById(id);
    } catch (EntityNotFoundException e) {
      log.error(e.getMessage());
      return new ResponseDto<>(null, ErrorCode.ENTITY_NOT_FOUND);
    } catch (Exception e) {
      log.error(e.getMessage());
      return new ResponseDto<>(null, ErrorCode.ENTITY_NOT_FOUND);
    }
    return new ResponseDto<>(postingDetailResponseDto);
  }


  //게시글 수정
  @PutMapping("/api/post/{id}")//리턴값이 게시글 작성과 같음..
  public ResponseDto<PostingResponseDto> updatePosting(@PathVariable Long id,
      @RequestBody PostingRequestDto requestDto) {
    PostingResponseDto postPostingResponseDto;
    try {
      postPostingResponseDto = postingService.update(id, requestDto);

    } catch (CustomException e) {//아이디 못찾을때 에러를 미리 지정을 했으므로 해당 에러를 감지하여 먼저 가져옴
      log.error(e.getMessage());//에러메세지 로그에 띄움
      return new ResponseDto<>(null, e.getErrorCode());

    } catch (Exception e) {
      log.error(e.getMessage());
      return new ResponseDto<>(null, ErrorCode.INVALID_ERROR);
    }
    return new ResponseDto<>(postPostingResponseDto);
  }

  //게시글 삭제
  @DeleteMapping("/api/post/{id}")//로직 실행 여부를 보는 값이니까 실행이 되었다 아니다만 띄우면됨->결과값을 받아줄 변수가 필요없음.
  public ResponseDto<String> deletePosting(@PathVariable Long id) {
    try {
      postingService.delete(id);
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
