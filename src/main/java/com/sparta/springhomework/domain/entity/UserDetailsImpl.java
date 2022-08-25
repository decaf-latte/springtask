package com.sparta.springhomework.domain.entity;

import java.util.ArrayList;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

  private Member member;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // enum의 이름을 String으로 가져 오고 싶으면 .name()을 사용해서 가져온다.
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
        this.member.getAuthority().name());

    Collection<GrantedAuthority> authorities = new ArrayList<>();

    authorities.add(authority);
    return authorities;
  }

  @Override
  public String getPassword() {
    return member.getPassword();
  }

  @Override
  public String getUsername() {
    return member.getNickname();
  }

  //만료 안되었음
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  //안잠겼음
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  //비밀번호 만료 안됨
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  //유저 사용가능?
  @Override
  public boolean isEnabled() {
    return true;
  }
}
