package com.jascoffee.jascoffee.config.user;

import com.jascoffee.jascoffee.jwt.CustomLogoutFilter;
import com.jascoffee.jascoffee.jwt.JWTFilter;
import com.jascoffee.jascoffee.jwt.LoginFilter;
import com.jascoffee.jascoffee.jwt.JWTUtil;

import com.jascoffee.jascoffee.repository.user.RefreshRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;


@Configuration
@EnableWebSecurity

public class SecurityConfig{

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil) {

        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, RefreshRepository refreshRepository) throws Exception{

        http
                .csrf((auth) -> auth.disable());

        http
                .formLogin((auth) -> auth.disable());

        http
                .httpBasic((auth) -> auth.disable());

        http
                .authorizeHttpRequests((auth)-> auth
                        .requestMatchers("/login","/","/join","/products","/orderdetail","api/orderlist","orderdetailAll").permitAll()
                        .requestMatchers("/reissue").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/users/change-password").authenticated()
                        .anyRequest().authenticated());
        http
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);

        http
                .addFilterAt(
                        new LoginFilter(authenticationManager(authenticationConfiguration),jwtUtil, refreshRepository), UsernamePasswordAuthenticationFilter.class);

        http
                .addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class);

        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // CORS 부분
        http
                .cors((corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);

                        configuration.setExposedHeaders(Arrays.asList("Authorization", "access", "refresh"));
                        return configuration;
                    }
                })));

        return http.build();
    }
    //수정전 re/eyJhbGciOiJIUzI1NiJ9.eyJjYXRlZ29yeSI6InJlZnJlc2giLCJhY2NvdW50IjoidGVzdCIsImlzU3RhZmYiOmZhbHNlLCJpYXQiOjE3MzQzMzMxNTcsImV4cCI6MTczNDQxOTE1N30.9WW2090kSv8HGHSY_7QjHq25lsy7gvyqW0_o-mGaXqA
    //수정 전 ac/eyJhbGciOiJIUzI1NiJ9.eyJjYXRlZ29yeSI6ImFjY2VzcyIsImFjY291bnQiOiJ0ZXN0IiwiaXNTdGFmZiI6ZmFsc2UsImlhdCI6MTczNDMzMzE1NywiZXhwIjoxNzM0MzM5MTU3fQ.LWTKy9G0OTAiahQX5_Gd2Fi6iv4t-_2bxBp27GvqXR4

    //수정후 re/eyJhbGciOiJIUzI1NiJ9.eyJjYXRlZ29yeSI6InJlZnJlc2giLCJhY2NvdW50IjoidGVzdCIsImlzU3RhZmYiOmZhbHNlLCJpYXQiOjE3MzQzMzMxNTcsImV4cCI6MTczNDQxOTE1N30.9WW2090kSv8HGHSY_7QjHq25lsy7gvyqW0_o-mGaXqA
}