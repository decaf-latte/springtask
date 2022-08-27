package com.sparta.springhomework.security;


import com.sparta.springhomework.jwt.JwtAccessDeniedHandler;
import com.sparta.springhomework.jwt.JwtAuthenticationEntryPoint;
import com.sparta.springhomework.jwt.TokenProvider;
import com.sparta.springhomework.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig {

  @Value("${jwt.secret}")
  String SECRET_KEY;

  private final UserDetailsServiceImpl userDetailsService;
  private final TokenProvider tokenProvider;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;


  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    // "/h2-console/**" -> 이 url 경로 : h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
    return (web) -> web.ignoring().antMatchers("/h2-console/**", "/favicon.ico");
  }

  @Bean
  @Order(SecurityProperties.BASIC_AUTH_ORDER)
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // CSRF 설정 Disable
    http.csrf().disable();

    // exception handling 할 때 우리가 만든 클래스를 추가
    http
        .exceptionHandling()
        .authenticationEntryPoint(jwtAuthenticationEntryPoint) // jwt 자체에 문제가 있는거
        .accessDeniedHandler(jwtAccessDeniedHandler); // jwt는 정상인데 접근하려는 url의 권한이 다를 때

    // 서버에서 인증은 JWT로 인증하기 때문에 Session의 생성을 막습니다.
    http
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.authorizeRequests()
        .antMatchers(HttpMethod.POST, "/api/member/signup").permitAll()
        .antMatchers(HttpMethod.POST, "/api/member/login").permitAll()
        .antMatchers(HttpMethod.GET, "/api/post").permitAll()
        .antMatchers(HttpMethod.GET, "/api/post/{id}").permitAll()
        .antMatchers(HttpMethod.GET, "/api/comment/{id}").permitAll()
        .anyRequest().hasRole("USER"); // ROLE_USER를 안 넣고 ROLE_ 을 빼고 넣어야한다.
//        .anyRequest().permitAll(); // 권한 체크를 안 함/ 토큰 확인은 함
//        .antMatchers("/api/**").hasRole("ROLE_USER"); // /api/**에 접근하기 위해서는 ROLE_USER의 권한을 가진 유저만 접근 가능하다.

    // JwtFilter 를 addFilterBefore 로 등록했던 JwtSecurityConfig 클래스를 적용

    http
        .apply(new JwtSecurityConfig(SECRET_KEY, tokenProvider, userDetailsService));

    return http.build();
  }

  @Bean
  public BCryptPasswordEncoder encodePassword() {
    return new BCryptPasswordEncoder();
  }
}


