package com.sparta.springhomework.service.impl;

import com.sparta.springhomework.domain.entity.LikePost;
import com.sparta.springhomework.domain.entity.Member;
import com.sparta.springhomework.domain.entity.Posting;
import com.sparta.springhomework.domain.enums.ErrorCode;
import com.sparta.springhomework.dto.response.ResponseDto;
import com.sparta.springhomework.exception.CustomException;
import com.sparta.springhomework.jwt.TokenProvider;
import com.sparta.springhomework.repository.LikePostingRepository;
import com.sparta.springhomework.service.LikeService;
import com.sparta.springhomework.service.PostingService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService {

  private final LikePostingRepository likePostingRepository;
  private final TokenProvider tokenProvider;
  private final PostingService postingService;


  @Override
  @Transactional
  public ResponseDto<String> togglePostLike(Long postId, HttpServletRequest request) {
    if (null == request.getHeader("Refresh-Token")) {
      throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
    }

    if (null == request.getHeader("Authorization")) {
      throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
    }

    Member member = validateMember(request);
    if (null == member) {
      throw new CustomException(ErrorCode.INVALID_TOKEN);
    }

    Posting post = postingService.findById(postId);
    if (null == post) {
      throw new CustomException(ErrorCode.POSTING_ID_NOT_FOUND);
    }

    LikePost checkLike = likePostingRepository.findByPostIdAndMemberId(post.getId(),
        member.getId());
    if (checkLike == null) {
      // like 등록
      LikePost likePost = LikePost.builder()
          .member(member)
          .posting(post)
          .build();
      likePostingRepository.save(likePost);
    } else {
      //like 취소
      likePostingRepository.deleteById(checkLike.getId());
    }

    // 해당 게시물 likes 업데이트
    Long likes = likePostingRepository.countAllByPostId(post.getId());
    post.updateLikes(likes);

    if (checkLike == null) {
      return new ResponseDto("like post success");
    } else {
      return new ResponseDto("Unlike post");
    }

  }

  public Member validateMember(HttpServletRequest request) {
    if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
      return null;
    }
    return tokenProvider.getMemberFromAuthentication();
  }

}
