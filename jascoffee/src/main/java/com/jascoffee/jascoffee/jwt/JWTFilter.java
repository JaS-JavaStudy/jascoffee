package com.jascoffee.jascoffee.jwt;

import com.jascoffee.jascoffee.dto.user.CustomUserDetails;
import com.jascoffee.jascoffee.entity.user.UserEntity;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //헤더에서 access키에 담긴 토큰을 꺼냄
        String accessToken = request.getHeader("access");

        //토큰이 없다면 다음 필터로 넘김
        if (accessToken == null) {

            filterChain.doFilter(request, response);

            return;
        }

        //토큰이 만료 여부 확인, 만료 시 다음 필터로 넘기지 않음
        try{
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e){

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("jwtfilter 안에서 알립니다. access token expired");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 토큰이 access인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(accessToken);

        if (!category.equals("access")) {

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("invalid access token");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // username, role 값을 획득

        String account = jwtUtil.getAccount(accessToken);
        boolean isStaff = jwtUtil.getIsStaff(accessToken);


        String role = isStaff ? "ROLE_ADMIN" : "ROLE_USER";
        UserEntity userEntity = new UserEntity();
        userEntity.setAccount(account);
        userEntity.setIsStaff(isStaff);

        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, Collections.singletonList(new SimpleGrantedAuthority(role)));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);

    }
    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setContentType("application/json");
        response.setStatus(status);
        PrintWriter writer = response.getWriter();
        writer.write("{\"error\": \"" + message + "\"}");
    }
}
