package co.com.crediya.auth.api.config.security.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtils {
  private static final String USER_ID = "userId";
  private static final String PASSWORD = "password";
  private static final String AUTHORITIES = "authorities";
  private static final String ROLE_TO_CONCAT = "ROLE_";

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.expiration}")
  private long jwtExpirationMs;

  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(jwtSecret.getBytes());
  }

  public String generateToken(Authentication authentication) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

    CustomUserDetails usrDetails = (CustomUserDetails) authentication.getPrincipal();

    List<String> roles =
        new ArrayList<>(
            usrDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(ROLE_TO_CONCAT::concat)
                .toList());

    return Jwts.builder()
        .setSubject(usrDetails.getUsername())
        .claim(AUTHORITIES, roles)
        .claim(USER_ID, usrDetails.getUserId())
        .setIssuedAt(now)
        .setNotBefore(now)
        .setExpiration(expiryDate)
        .setId(UUID.randomUUID().toString())
        .signWith(getSigningKey())
        .compact();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public UsernamePasswordAuthenticationToken getAuthenticationInfo(String token) {
    Claims claims =
        Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();

    String username = claims.getSubject();

    List<?> rawRoles = claims.get(AUTHORITIES, List.class);
    List<String> roles = rawRoles.stream().map(String::valueOf).toList();

    UUID userId = UUID.fromString(claims.get(USER_ID, String.class));
    List<SimpleGrantedAuthority> authorities =
        roles.stream().map(SimpleGrantedAuthority::new).toList();

    CustomUserDetails userDetails = new CustomUserDetails(username, PASSWORD, userId, authorities);

    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }
}
