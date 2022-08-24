package com.sparta.springhomework.security;

import com.sparta.springhomework.jwt.JwtFilter;
import com.sparta.springhomework.jwt.TokenProvider;
import com.sparta.springhomework.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// 직접 만든 TokenProvider 와 JwtFilter 를 SecurityConfig 에 적용할 때 사용
@RequiredArgsConstructor
public class JwtSecurityConfig extends
    SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {


  private final String SECRET_KEY;
  private final TokenProvider tokenProvider;

  private final UserDetailsServiceImpl userDetailsService;

  // TokenProvider 를 주입받아서 JwtFilter 를 통해 Security 로직에 필터를 등록
  @Override
  public void configure(HttpSecurity httpSecurity) {
    JwtFilter customFilter = new JwtFilter(SECRET_KEY, tokenProvider, userDetailsService);
    httpSecurity.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
  }
}