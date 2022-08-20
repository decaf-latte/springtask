package com.sparta.springhomework.service;

import com.sparta.springhomework.domain.entity.Posting;
import com.sparta.springhomework.repository.PostingRepository;
import com.sparta.springhomework.domain.dto.PostingRequestDto;
import com.sparta.springhomework.domain.dto.PostingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Objects;


@RequiredArgsConstructor
@Service
public class PostingService {

    private final PostingRepository postingRepository;



    @Transactional // 메소드 동작이 SQL 쿼리문임을 선언합니다.
    public Posting update(Long id, PostingRequestDto requestDto) {
        Posting posting = postingRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("해당 아이디가 존재하지 않습니다."+id)
        );
        posting.update(requestDto);
        return posting;
    }

    //비밀번호 비교
    public boolean comparePassword ( Long id, PostingRequestDto.PostingPasswordDto postingPasswordDto) {

        Posting posting = postingRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("해당 아이디가 존재하지 않습니다." +id));
        return Objects.equals(posting.getPassword(), postingPasswordDto.getPassword());
    }

    //게시글 조회시

    public PostingResponseDto findById (Long id) {
        Posting entity = postingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글이 없습니다 id =" +id));
        return new PostingResponseDto(entity);
    }

}
