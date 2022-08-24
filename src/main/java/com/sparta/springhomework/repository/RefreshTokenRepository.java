package com.sparta.springhomework.repository;

import com.sparta.springhomework.domain.entity.Member;
import com.sparta.springhomework.domain.entity.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

  Optional<RefreshToken> findByMember(Member member);
}