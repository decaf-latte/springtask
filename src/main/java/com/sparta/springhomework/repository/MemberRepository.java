package com.sparta.springhomework.repository;

import com.sparta.springhomework.domain.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {


  Optional<Member> findByNickname(String nickname);

}
