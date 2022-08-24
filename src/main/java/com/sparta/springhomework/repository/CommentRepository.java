package com.sparta.springhomework.repository;

import com.sparta.springhomework.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
