package com.jascoffee.jascoffee.controller;

import com.jascoffee.jascoffee.dto.user.CustomUserDetails;
import com.jascoffee.jascoffee.jwt.JWTUtil;
import com.jascoffee.jascoffee.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@ResponseBody
public class MainController {

    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public String mainP() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            System.out.println(authentication.getName());
        }else{
            System.out.println("Authentication is null");
        }


        // 현재 인증된 사용자 정보 가져오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principal);
        // principal 이 UserDetails 타입으로 캐스팅 가능한지 확인
        if (principal instanceof UserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            System.out.println(userDetails);
            // account 와 isStaff 가져오기
            String account = userDetails.getAccount();
            boolean isStaff = userDetails.getIsStaff();
            System.out.println(account);

            return "Main Controller : Account = " + account + ", Is Staff = " + isStaff;
        }
        return "User is not authenticated";
    }

    @DeleteMapping("/user/delete")
    public String deleteUser(@RequestHeader Authentication authentication) {
        System.out.println("authentication"+ authentication);
        if (authentication == null || !authentication.isAuthenticated()) {
            return "인증되지 않은 사용자입니다.";
        }

        // 현재 로그인된 사용자 계정 가져오기
        String account = authentication.getName();
        System.out.println("account"+ account );

        try {
            // 회원탈퇴 처리
            userService.deleteUser(account);

            // 인증 세션 초기화
            SecurityContextHolder.clearContext();

            return "회원탈퇴가 완료되었습니다.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

    }
}
