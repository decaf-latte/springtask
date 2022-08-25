package com.sparta.springhomework.controller;

import com.sparta.springhomework.domain.dto.PostPostingResponseDto;
import com.sparta.springhomework.domain.dto.PostingRequestDto;
import com.sparta.springhomework.domain.dto.PostingResponseDto;
import com.sparta.springhomework.domain.dto.ResponseDto;
import com.sparta.springhomework.domain.entity.Member;
import com.sparta.springhomework.domain.entity.Posting;
import com.sparta.springhomework.domain.entity.UserDetailsImpl;
import com.sparta.springhomework.domain.enums.ErrorCode;
import com.sparta.springhomework.repository.PostingRepository;
import com.sparta.springhomework.service.PostingService;
import java.util.ArrayList;
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

  private final PostingRepository postingRepository;
  private final PostingService postingService;

  //전체조회
  @GetMapping("/api/post")
  public ResponseDto<List<PostingResponseDto>> getPosting() {
    List<PostingResponseDto> data;
    try {
      List<Posting> postings = postingRepository.findAllByOrderByModifiedAtDesc();

      data = new ArrayList<>();

      for (Posting posting : postings) {
        PostingResponseDto postingResponseDto = new PostingResponseDto(posting);
        data.add(postingResponseDto);
      }

    } catch (Exception e) {
      return new ResponseDto<>(null, ErrorCode.INVALID_ERROR);
    }
    return new ResponseDto<>(data);
  }

  //게시글 조회
  @GetMapping("/api/post/{id}")
  public ResponseDto<PostingResponseDto> getPosting(@PathVariable Long id) {
    PostingResponseDto postingResponseDto;
    try {
      postingResponseDto = postingService.findById(id);
    } catch (EntityNotFoundException e) {
      log.error(e.getMessage());
      return new ResponseDto<>(null, ErrorCode.ENTITY_NOT_FOUND);
    } catch (Exception e) {
      log.error(e.getMessage());
      return new ResponseDto<PostingResponseDto>(null, ErrorCode.ENTITY_NOT_FOUND);
    }

    return new ResponseDto<PostingResponseDto>(postingResponseDto);
  }

  //게시글 작성
  @PostMapping("/api/post")//리스폰스디티오 따로 ..리턴되는 데이터값이다름.
  public ResponseDto<PostPostingResponseDto> createPosting(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody PostingRequestDto requestDto) {
    PostPostingResponseDto postPostingResponseDto;

    try {
      Member member = userDetails.getMember();
      Posting posting = new Posting(requestDto, member); //작성 데이터 받음
      posting = postingRepository.save(posting); // 작성데이터+메모리저장 ID추가됨
      postPostingResponseDto = new PostPostingResponseDto(posting);

    } catch (Exception e) {
      return new ResponseDto<>(null, ErrorCode.INVALID_ERROR);
    }
    return new ResponseDto<>(postPostingResponseDto);

  }

  //게시글 비밀번호 확인(비교)//..??
  @PostMapping("/api/post/{id}")//comparepassword 로직 리턴 값이 boolean이기때문에 값을 받아주는 변수를 data
  public ResponseDto<Boolean> comparePassword(@PathVariable Long id,
      @RequestBody PostingRequestDto.PostingPasswordDto postingPasswordDto) {
    boolean data;
    try {//기본 템플렛
      data = postingService.comparePassword(id, postingPasswordDto);
    } catch (EntityNotFoundException e) {//아이디 못찾을때 에러를 미리 지정을 했으므로 해당 에러를 감지하여 먼저 가져옴
      log.error(e.getMessage());//에러메세지 로그에 띄움
      return new ResponseDto<>(null, ErrorCode.ENTITY_NOT_FOUND);
    } catch (Exception e) {//나머지 에러들
      log.error(e.getMessage());//에러메세지 로그에 띄움
      return new ResponseDto<>(null, ErrorCode.INVALID_ERROR);
    }
    return new ResponseDto<>(data);
  }

  //게시글 수정
  @PutMapping("/api/post/{id}")//리턴값이 게시글 작성과 같음..
  public ResponseDto<PostPostingResponseDto> updatePosting(@PathVariable Long id,
      @RequestBody PostingRequestDto requestDto) {
    PostPostingResponseDto postPostingResponseDto;

    try {
      Posting posting = postingService.update(id, requestDto);
      postPostingResponseDto = new PostPostingResponseDto(posting);

    } catch (EntityNotFoundException e) {//아이디 못찾을때 에러를 미리 지정을 했으므로 해당 에러를 감지하여 먼저 가져옴
      log.error(e.getMessage());//에러메세지 로그에 띄움
      return new ResponseDto<>(null, ErrorCode.ENTITY_NOT_FOUND);
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
    } catch (Exception e) {
      String data = "false";
      return new ResponseDto<>(data, ErrorCode.ENTITY_NOT_FOUND);
    }
    String data = "delete success";

    return new ResponseDto<>(data);
  }
}
