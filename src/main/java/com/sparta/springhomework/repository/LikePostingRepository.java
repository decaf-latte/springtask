package com.sparta.springhomework.repository;

import com.sparta.springhomework.domain.entity.LikePost;
import com.sparta.springhomework.domain.entity.Member;
import com.sparta.springhomework.domain.entity.Posting;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePostingRepository extends JpaRepository<LikePost, Long> {

  Long countAllByPosting(Posting posting);

  List<LikePost> findByMember(Member member);

  LikePost findByPostingAndMember(Posting posting, Member member);
}
