package com.jascoffee.jascoffee.jwt;

import com.jascoffee.jascoffee.dto.user.CustomUserDetails;
import com.jascoffee.jascoffee.entity.user.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // request에서 Authorization 헤더를 찾음
        String authorization = request.getHeader("Authorization");

        // authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("authorization now");
        String token = authorization.split(" ")[1];

        // 토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {
            System.out.println("token is expired");
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰에서 account와 role 획득
        String account = jwtUtil.getAccount(token);  // getUsername 메서드는 account 값을 반환해야 함

        UserEntity userEntity = new UserEntity();
        userEntity.setAccount(account);  // account 필드로 설정
        userEntity.setPassword("temppassword");

        // userDetail에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        // 스프링 시큐리티 인증 토큰 형성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        // 세션 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
