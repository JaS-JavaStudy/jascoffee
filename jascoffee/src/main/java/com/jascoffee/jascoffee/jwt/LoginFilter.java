package com.jascoffee.jascoffee.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jascoffee.jascoffee.dto.user.CustomUserDetails;
import com.jascoffee.jascoffee.dto.user.JoinDTO;
import com.jascoffee.jascoffee.entity.user.RefreshEntity;
import com.jascoffee.jascoffee.repository.user.RefreshRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    // JSON으로 받기 위한
    private final ObjectMapper objectMapper = new ObjectMapper();


    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, RefreshRepository refreshRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;

    }

    // account request를 username으로 변경해야함
//    @Override
//    protected String obtainUsername(HttpServletRequest request) {
//        return request.getParameter("account");
//    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        // 1.JSON 버전 (요청 본문에서 JSON 데이터를 읽어오기)
        String account = null;
        String password = null;

        try {
            // JSON 데이터 파싱
            JoinDTO joinDTO = objectMapper.readValue(request.getInputStream(), JoinDTO.class);
            account = joinDTO.getAccount();  // JoinDTO에서 username 추출
            password = joinDTO.getPassword();  // JoinDTO에서 password 추출
        } catch (IOException e) {
            e.printStackTrace();
            throw new AuthenticationException("Failed to parse JSON request") {};
        }

        // 2. form-data버전 (클라이언트 요청에서 username, password를 추출)
//        String username = obtainUsername(request);
//        String password = obtainPassword(request);

        // 스프링 시큐리티에서 username과 password를 검증하기 위해 token에 담음
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(account, password, null);

        // 담은 토큰을 AuthenticationManager로 전달
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        //유저 정보
        String username = authentication.getName();
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
//        GrantedAuthority auth = iterator.next();
        //String role = auth.getAuthority();

        String isStaff = String.valueOf(customUserDetails.getIsStaff());

        //토큰 생성
        Boolean isStaffBoolean = Boolean.parseBoolean(isStaff); // 만약 isStaff가 String이라면 변환
        String newAccess = jwtUtil.createJwt("access", username, isStaffBoolean, 600000L);
        String newRefresh = jwtUtil.createJwt("refresh", username, isStaffBoolean, 86400000L);


        //Refresh 토큰 저장
        addRefreshEntity(username, newRefresh, 86400000L);

        response.setHeader("access",newAccess);
        response.addCookie(createCookie("refresh",newRefresh));
        response.setStatus(HttpStatus.OK.value());

    }

    private Cookie createCookie(String key, String value){

        Cookie cookie = new Cookie(key,value);
        cookie.setMaxAge(24*60*60);
        //cookie.setSecure(true);
        //cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        System.out.println("메롱 실패했지롱");
        // 인증 실패 시 상태 코드 설정
        response.setStatus(401);
    }

    private void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setAccount(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }
}

