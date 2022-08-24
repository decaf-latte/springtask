package com.sparta.springhomework.jwt;

import com.sparta.springhomework.domain.dto.ResponseDto;
import com.sparta.springhomework.domain.dto.TokenDto;
import com.sparta.springhomework.domain.entity.Member;
import com.sparta.springhomework.domain.entity.RefreshToken;
import com.sparta.springhomework.domain.entity.UserDetailsImpl;
import com.sparta.springhomework.domain.enums.Authority;
import com.sparta.springhomework.domain.enums.ErrorCode;
import com.sparta.springhomework.repository.RefreshTokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Component
public class TokenProvider {

  private static final String AUTHORITIES_KEY = "auth";
  private static final String BEARER_TYPE = "bearer";
  private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            // 30분
  private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일

  private final Key key;

  private RefreshTokenRepository refreshTokenRepository;

  public TokenProvider(@Value("${jwt.secret}") String secretKey,
      RefreshTokenRepository refreshTokenRepository) {
    this.refreshTokenRepository = refreshTokenRepository;
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  public TokenDto generateTokenDto(Member member) {
    // 권한들 가져오기
//    String authorities = authentication.getAuthorities().stream()
//        .map(GrantedAuthority::getAuthority)
//        .collect(Collectors.joining(","));

    long now = (new Date()).getTime();

    // Access Token 생성
    Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
    String accessToken = Jwts.builder()
        .setSubject(member.getNickname())       // payload "sub": "name"
        .claim(AUTHORITIES_KEY,
            Authority.ROLE_USER.toString())        // payload "auth": "ROLE_USER"
        .setExpiration(accessTokenExpiresIn)        // payload "exp": 1516239022 (예시)
        .signWith(key, SignatureAlgorithm.HS256)    // header "alg": "HS512"
        .compact();

    // Refresh Token 생성
    String refreshToken = Jwts.builder()
        .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();

    RefreshToken refreshTokenObject = RefreshToken.builder()
        .id(member.getId())
        .member(member)
        .value(refreshToken)
        .build();

    refreshTokenRepository.save(refreshTokenObject);

    return TokenDto.builder()
        .grantType(BEARER_TYPE)
        .accessToken(accessToken)
        .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
        .refreshToken(refreshToken)
        .build();
  }

//  public Authentication getAuthentication(String accessToken) {
//    // 토큰 복호화
//    Claims claims = parseClaims(accessToken);
//
//    if (claims.get(AUTHORITIES_KEY) == null) {
//      throw new RuntimeException("권한 정보가 없는 토큰입니다.");
//    }
//
//    // 클레임에서 권한 정보 가져오기
//    Collection<? extends GrantedAuthority> authorities =
//        Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
//            .map(SimpleGrantedAuthority::new)
//            .collect(Collectors.toList());
//
//    // UserDetails 객체를 만들어서 Authentication 리턴
//    UserDetails principal = new User(claims.getSubject(), "", authorities);
//
//    return new UsernamePasswordAuthenticationToken(principal, "", authorities);
//  }

  public Member getMemberFromAuthentication() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || AnonymousAuthenticationToken.class.
        isAssignableFrom(authentication.getClass())) {
      return null;
    }
    return ((UserDetailsImpl) authentication.getPrincipal()).getMember();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
      log.info("잘못된 JWT 서명입니다.");
    } catch (ExpiredJwtException e) {
      log.info("만료된 JWT 토큰입니다.");
    } catch (UnsupportedJwtException e) {
      log.info("지원되지 않는 JWT 토큰입니다.");
    } catch (IllegalArgumentException e) {
      log.info("JWT 토큰이 잘못되었습니다.");
    }
    return false;
  }

  //  private Claims parseClaims(String accessToken) {
//    try {
//      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
//    } catch (ExpiredJwtException e) {
//      return e.getClaims();
//    }
//  }
  @Transactional(readOnly = true)
  public RefreshToken isPresentRefreshToken(Member member) {
    Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByMember(member);
    return optionalRefreshToken.orElse(null);
  }

  @Transactional
  public ResponseDto<?> deleteRefreshToken(Member member) {
    RefreshToken refreshToken = isPresentRefreshToken(member);
    if (null == refreshToken) {
      return new ResponseDto("", ErrorCode.BAD_REQUEST);
    }

    refreshTokenRepository.delete(refreshToken);
    return new ResponseDto("삭제");
  }

}