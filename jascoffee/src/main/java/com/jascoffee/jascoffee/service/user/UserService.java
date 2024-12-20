package com.jascoffee.jascoffee.service.user;

import com.jascoffee.jascoffee.dto.user.CustomUserDetails;
import com.jascoffee.jascoffee.dto.user.PasswordUpdateDTO;
import com.jascoffee.jascoffee.dto.user.UserUpdateDTO;
import com.jascoffee.jascoffee.entity.user.UserEntity;
import com.jascoffee.jascoffee.repository.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void updateUser(UserUpdateDTO request) throws IllegalArgumentException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                "anonymousUser".equals(authentication.getPrincipal())) {
            throw new IllegalStateException("인증되지 않은 사용자입니다.");
        }

        String account = null;

        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            account = userDetails.getAccount();
            System.out.println("Account: " + account);
        }

        if (account == null) {
            throw new IllegalStateException("현재 사용자의 계정 정보를 가져올 수 없습니다.");
        }

        UserEntity user = userRepository.findByAccount(account)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // account 변경 요청 처리
        if (request.getAccount() != null) {
            if (!request.getAccount().equals(user.getAccount())
                    && userRepository.existsByAccount(request.getAccount())) {
                throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
            }
            user.setAccount(request.getAccount());
        }

        // mmid 변경 요청 처리
        if (request.getMmid() != null) {
            if (!request.getMmid().equals(user.getMmid())
                    && userRepository.existsByMmid(request.getMmid())) {
                throw new IllegalArgumentException("이미 사용중인 mmid 입니다.");
            }
            user.setMmid(request.getMmid());
        }

        if(request.getFund() != null) {
            user.setFund(request.getFund());
        }

        if(request.getName() != null) {
            user.setName(request.getName());
        }

        System.out.println("수정 후 id" + user + "수정 후 mmid"+ user.getMmid());
        userRepository.save(user);
    }

    public void updatePassword(PasswordUpdateDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() ||
                "anonymousUser".equals(authentication.getPrincipal())) {
            throw new IllegalStateException("인증되지 않은 사용자입니다.");
        }

        String account = null;

        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            account = userDetails.getAccount();
            System.out.println("Account: " + account);
        }

        if (account == null) {
            throw new IllegalStateException("현재 사용자의 계정 정보를 가져올 수 없습니다.");
        }

        UserEntity user = userRepository.findByAccount(account)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }


}
