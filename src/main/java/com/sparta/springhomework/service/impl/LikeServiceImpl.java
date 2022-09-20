package com.sparta.springhomework.service.impl;

import com.sparta.springhomework.domain.entity.LikePost;
import com.sparta.springhomework.domain.entity.Member;
import com.sparta.springhomework.domain.entity.Posting;
import com.sparta.springhomework.domain.response.ResponseDto;
import com.sparta.springhomework.jwt.TokenProvider;
import com.sparta.springhomework.repository.CommentRepository;
import com.sparta.springhomework.repository.LikePostingRepository;
import com.sparta.springhomework.repository.ReplyRepository;
import com.sparta.springhomework.service.CommentService;
import com.sparta.springhomework.service.LikeService;
import com.sparta.springhomework.service.PostingService;
import com.sparta.springhomework.service.ReplyService;
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
  private final CommentRepository commentRepository;
  private final CommentService commentService;
  private final ReplyRepository replyRepository;
  private final ReplyService replyService;

  @Override
  @Transactional
  public ResponseDto<?> togglePostLike(Long postId, HttpServletRequest request) {
    if (null == request.getHeader("Refresh-Token")) {
      return ResponseDto.fail("MEMBER_NOT_FOUND", "로그인이 필요합니다.");
    }

    if (null == request.getHeader("Authorization")) {
      return ResponseDto.fail("MEMBER_NOT_FOUND", "로그인이 필요합니다.");
    }

    Member member = validateMember(request);
    if (null == member) {
      return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
    }

    Posting post = postingService.isPresentPost(postId);
    if (null == post) {
      return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
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
      return ResponseDto.success("like post success");
    } else {
      return ResponseDto.success("like post cancel");
    }

  }

  public Member validateMember(HttpServletRequest request) {
    if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
      return null;
    }
    return tokenProvider.getMemberFromAuthentication();
  }

}
