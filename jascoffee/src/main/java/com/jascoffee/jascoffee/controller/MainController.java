package com.jascoffee.jascoffee.controller;

import com.jascoffee.jascoffee.dto.user.CustomUserDetails;
import com.jascoffee.jascoffee.jwt.JWTUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@ResponseBody
public class MainController {

    @GetMapping("/")
    public String mainP() {

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
}
