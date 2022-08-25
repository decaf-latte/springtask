package com.sparta.springhomework.repository;

import com.sparta.springhomework.domain.entity.Member;
import com.sparta.springhomework.domain.entity.TokenStore;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenStoreRepository extends JpaRepository<TokenStore, Long> {

  Optional<TokenStore> findByMember(Member member);
}