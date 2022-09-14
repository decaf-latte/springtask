package com.sparta.springhomework.jwt;

import com.sparta.springhomework.domain.TokenDto;
import com.sparta.springhomework.domain.entity.Member;
import com.sparta.springhomework.domain.entity.TokenStore;
import com.sparta.springhomework.domain.entity.UserDetailsImpl;
import com.sparta.springhomework.domain.enums.ErrorCode;
import com.sparta.springhomework.domain.response.ResponseDto;
import com.sparta.springhomework.repository.TokenStoreRepository;
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

  private final TokenStoreRepository tokenStoreRepository;

  public TokenProvider(@Value("${jwt.secret}") String secretKey,
      TokenStoreRepository tokenStoreRepository) {
    this.tokenStoreRepository = tokenStoreRepository;
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
        .claim(AUTHORITIES_KEY, member.getAuthority())        // payload "auth": "ROLE_USER"
        .setExpiration(accessTokenExpiresIn)        // payload "exp": 1516239022 (예시)
        .signWith(key, SignatureAlgorithm.HS256)    // header "alg": "HS512"
        .compact();

    // Refresh Token 생성
    String refreshToken = Jwts.builder()
        .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();

    TokenStore tokenStore = new TokenStore(member, refreshToken);
    tokenStoreRepository.save(tokenStore);

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


  //스프링 시큐리티- 컨텍스트 홀더안에 저장된 회원정보를 확인하는 것
  public Member getMemberFromAuthentication() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(
        authentication.getClass())) {

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
  public TokenStore isPresentRefreshToken(Member member) {
    Optional<TokenStore> optionalRefreshToken = tokenStoreRepository.findByMember(member);
    return optionalRefreshToken.orElse(null);
  }

  @Transactional
  public ResponseDto<?> deleteRefreshToken(Member member) {
    TokenStore tokenStore = isPresentRefreshToken(member);
    if (null == tokenStore) {
      return new ResponseDto(ErrorCode.BAD_REQUEST);
    }

    tokenStoreRepository.delete(tokenStore);
    return new ResponseDto<>("삭제");
  }

}
