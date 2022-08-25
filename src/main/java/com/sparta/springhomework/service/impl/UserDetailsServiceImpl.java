package com.sparta.springhomework.service.impl;

import com.sparta.springhomework.domain.entity.Member;
import com.sparta.springhomework.domain.entity.UserDetailsImpl;
import com.sparta.springhomework.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Member> optionalMember = memberRepository.findByNickname(username);

    if (optionalMember.isPresent()) {
      Member member = optionalMember.get();
      return new UserDetailsImpl(member);
    }

    throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");

//    return member
//        .map(UserDetailsImpl::new)
//        .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
  }
}

//  // DB 에 User 값이 존재한다면 UserDetails 객체로 만들어서 리턴
//  private UserDetails createUserDetails(Member member) {
//    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
//        member.getAuthority().toString());
//
//    return new User(
//        String.valueOf(member.getId()),
//        member.getPassword(),
//        Collections.singleton(grantedAuthority)
//    );
//  }
//}
