package com.jascoffee.jascoffee.controller.user;

import com.jascoffee.jascoffee.dto.user.JoinDTO;
import com.jascoffee.jascoffee.jwt.JWTUtil;
import com.jascoffee.jascoffee.service.user.JoinService;
import org.springframework.web.bind.annotation.*;

@RestController  // @Controller + @ResponseBody 결합
public class JoinController {

    private final JoinService joinService;
    private JWTUtil jwtUtil;

    public JoinController (JoinService joinService, JWTUtil jwtUtil){
        this.joinService = joinService;
        this.jwtUtil = jwtUtil;
    }

    // @RequestBody를 사용하여 JSON 형식으로 데이터를 받을 때
    @PostMapping("/join")
    public String joinProcess(@RequestBody JoinDTO joinDTO) {
        joinService.joinProcess(joinDTO);
        return "ok";
    }

    // @ModelAttribute를 사용하여 form-data를 받을 때
//    @PostMapping("/join/form")
//    public String joinProcessForm(JoinDTO joinDTO) {
//        joinService.joinProcess(joinDTO);
//        return "ok";

//    }

    // 회원 아이디 조회
    @GetMapping("/user")
    public String getUserByAccount(@RequestHeader String access) {
        // account를 통해 DB에서 회원 정보 조회
        String account = jwtUtil.getAccount(access);

        return account;
    }

    // 회원 관리자 여부 조회
    @GetMapping("/staff")
    public boolean getStaffByAccount(@RequestHeader String access) {
        // account를 통해 DB에서 회원 정보 조회
        boolean isStaff = Boolean.getBoolean(jwtUtil.getIsStaff(access));

        return isStaff;
    }

}
