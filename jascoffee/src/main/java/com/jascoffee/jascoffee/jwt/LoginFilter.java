package com.jascoffee.jascoffee.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jascoffee.jascoffee.dto.user.CustomUserDetails;
import com.jascoffee.jascoffee.dto.user.JoinDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    // JSON으로 받기 위한
    private final ObjectMapper objectMapper = new ObjectMapper();

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    // account request를 username으로 변경해야함
    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter("account");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        // 1.JSON 버전 (요청 본문에서 JSON 데이터를 읽어오기)
        String username = null;
        String password = null;

        try {
            // JSON 데이터 파싱
            JoinDTO joinDTO = objectMapper.readValue(request.getInputStream(), JoinDTO.class);
            username = joinDTO.getAccount();  // JoinDTO에서 username 추출
            password = joinDTO.getPassword();  // JoinDTO에서 password 추출
        } catch (IOException e) {
            e.printStackTrace();
            throw new AuthenticationException("Failed to parse JSON request") {};
        }

        // 2. form-data버전 (클라이언트 요청에서 username, password를 추출)
//        String username = obtainUsername(request);
//        String password = obtainPassword(request);

        // 스프링 시큐리티에서 username과 password를 검증하기 위해 token에 담음
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        // 담은 토큰을 AuthenticationManager로 전달
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {

        // UserDetails
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        System.out.println(customUserDetails.getIsStaff());
        //사용자 이름을 가져옴
        String account = customUserDetails.getAccount();
        // 권한을 가져옴, 여러 권한이 있을 수 있으므로 첫 번째 권한을 사용하거나, 적절한 로직을 추가해야 함
//        Collection<? extends GrantedAuthority> authorities = customUserDetails.getAuthorities();
        boolean isStaff = customUserDetails.getIsStaff();  // 기본값을 설정

        // JWT 생성
        String token = jwtUtil.createJwt(account, isStaff, 60 * 60 * 10L);

        // 응답에 JWT 토큰 추가
        response.addHeader("Authorization", "Bearer " + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        // 인증 실패 시 상태 코드 설정
        response.setStatus(401);
    }
}

