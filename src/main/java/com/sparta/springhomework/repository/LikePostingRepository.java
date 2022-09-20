package com.sparta.springhomework.repository;

import com.sparta.springhomework.domain.entity.LikePost;
import com.sparta.springhomework.domain.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePostingRepository extends JpaRepository<LikePost, Long> {

  Long countAllByPostId(Long postId);

  List<LikePost> findByMember(Member member);

  LikePost findByPostIdAndMemberId(Long postId, Long memberId);
}
