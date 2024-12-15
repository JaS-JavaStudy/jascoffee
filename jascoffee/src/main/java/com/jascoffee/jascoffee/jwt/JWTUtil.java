package com.jascoffee.jascoffee.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {
    private final SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
    }

    // 토큰에서 account를 가져오는 메서드
    public String getAccount(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("account", String.class);
    }

    // 토큰에서 role을 가져오는 메서드
    public String getRole(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("role", String.class);
    }

    // 토큰의 만료 여부 확인
    public Boolean isExpired(String token) {
        try {
            // JWT 파서를 설정할 때 시간 차이를 허용하도록 구성
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .setAllowedClockSkewSeconds(60) // 최대 60초의 시간 차이 허용
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // 만료 시간을 확인
            System.out.println("Token Expiration Time: " + claims.getExpiration());
            System.out.println("커렌트 Current Time: " + new Date());
            return claims.getExpiration().before(new Date()); // 만료 여부 반환
        } catch (ExpiredJwtException e) {
            // JWT가 만료된 경우 처리
            System.out.println("Token expired: " + e.getMessage());
            return true; // 만료된 것으로 간주
        } catch (Exception e) {
            // 기타 예외 처리
            e.printStackTrace();
            return true; // 유효하지 않은 경우도 만료된 것으로 처리
        }
    }


    // JWT 생성 메서드
    public String createJwt(String account, boolean isStaff, Long expiredMs) {
        System.out.println("Token Expired Time: " + expiredMs);
        return Jwts.builder()
                .claim("account", account)
                .claim("isStaff", isStaff)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
