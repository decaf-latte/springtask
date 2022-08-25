package com.sparta.springhomework.service;

import com.sparta.springhomework.domain.dto.PostingRequestDto;
import com.sparta.springhomework.domain.dto.PostingResponseDto;
import com.sparta.springhomework.domain.entity.Member;
import com.sparta.springhomework.domain.entity.Posting;
import com.sparta.springhomework.domain.entity.UserDetailsImpl;
import com.sparta.springhomework.domain.enums.ErrorCode;
import com.sparta.springhomework.exception.CustomException;
import com.sparta.springhomework.repository.PostingRepository;
import java.util.Objects;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class PostingService {

  private final PostingRepository postingRepository;


  @Transactional // 메소드 동작이 SQL 쿼리문임을 선언합니다.
  public Posting update(Long id, PostingRequestDto requestDto) {
    Posting posting = postingRepository.findById(id).orElseThrow(
        () -> new EntityNotFoundException("해당 아이디가 존재하지 않습니다." + id)
    );
    //TODO: 이거보면서 posting delete, comment update, comment delete 수정
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    Member currentMember = userDetails.getMember();

    if (!Objects.equals(posting.getMember(), currentMember)) {
      throw new CustomException(ErrorCode.NOT_SAME_MEMBER);
    }

    posting.update(requestDto);
    return posting;
  }

  //비밀번호 비교
  public boolean comparePassword(Long id, PostingRequestDto.PostingPasswordDto postingPasswordDto) {

    Posting posting = postingRepository.findById(id).orElseThrow(
        () -> new EntityNotFoundException("해당 아이디가 존재하지 않습니다." + id));
    return Objects.equals(posting.getPassword(), postingPasswordDto.getPassword());
  }

  //게시글 조회시
  public PostingResponseDto findById(Long id) {
    Posting entity = postingRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("해당 게시글이 없습니다 id =" + id));
    return new PostingResponseDto(entity);
  }

  public Posting get(Long id) {
    return postingRepository.findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
  }

  @Transactional
  public void delete(Long id) {
    Posting posting = postingRepository.findById(id).orElseThrow(
        () -> new EntityNotFoundException("해당 아이디가 존재하지 않습니다." + id)
    );
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    Member currentMember = userDetails.getMember();

    if (!Objects.equals(posting.getMember(), currentMember)) {
      throw new CustomException(ErrorCode.NOT_SAME_MEMBER);

    }
    postingRepository.deleteById(id);
  }
}
