package com.sparta.springhomework.repository;

import com.sparta.springhomework.domain.entity.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePostingRepository extends JpaRepository<LikePost, Long> {

}
