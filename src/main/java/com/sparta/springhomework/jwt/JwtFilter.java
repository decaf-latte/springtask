package com.sparta.springhomework.jwt;

import com.sparta.springhomework.service.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import java.security.Key;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String BEARER_PREFIX = "Bearer ";

  public static String AUTHORITIES_KEY = "auth";

  private final String SECRET_KEY;

  private final TokenProvider tokenProvider;

  private final UserDetailsServiceImpl userDetailsService;

  // 실제 필터링 로직은 doFilterInternal 에 들어감
  // JWT 토큰의 인증 정보를 현재 쓰레드의 SecurityContext 에 저장하는 역할 수행
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws IOException, ServletException {

    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    Key key = Keys.hmacShaKeyFor(keyBytes);

    // 1. Request Header 에서 토큰을 꺼냄
    String jwt = resolveToken(request);

    // 2. validateToken 으로 토큰 유효성 검사
    // 정상 토큰이면 해당 토큰으로 Authentication 을 가져와서 SecurityContext 에 저장
    if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
      Claims claims;
      try {
        claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
      } catch (ExpiredJwtException e) {
        claims = e.getClaims();
      }
      //코튼이 유효하지 않을때
      if (claims.getExpiration().toInstant().toEpochMilli() < Instant.now().toEpochMilli()) {
        response.setContentType("application/json;charset=UTF-8");
//        response.getWriter().println(
//            new ObjectMapper().writeValueAsString(
//                ResponseDto<>(null, ErrorCode.BAD_REQUEST)
//      )
//        );
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      }

      String subject = claims.getSubject();
      Collection<? extends GrantedAuthority> authorities =
          Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
              .map(SimpleGrantedAuthority::new)
              .collect(Collectors.toList());

      UserDetails principal = userDetailsService.loadUserByUsername(subject);

      Authentication authentication = new UsernamePasswordAuthenticationToken(principal, jwt,
          authorities);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);

  }


  // Request Header 에서 토큰 정보를 꺼내오기
  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
      return bearerToken.substring(7);
    }
    return null;
  }
}
