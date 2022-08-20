package com.sparta.springhomework.repository;

import com.sparta.springhomework.domain.entity.Posting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostingRepository extends JpaRepository<Posting, Long> {

    List<Posting> findAllByOrderByModifiedAtDesc();
}
