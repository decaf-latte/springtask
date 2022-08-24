package com.sparta.springhomework.domain.entity;

import com.sparta.springhomework.domain.enums.Authority;
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
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(Authority.ROLE_USER.toString());
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
