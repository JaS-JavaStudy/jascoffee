package com.jascoffee.jascoffee.controller.user;

import com.jascoffee.jascoffee.dto.user.JoinDTO;
import com.jascoffee.jascoffee.entity.user.UserEntity;
import com.jascoffee.jascoffee.jwt.JWTUtil;
import com.jascoffee.jascoffee.repository.user.UserRepository;
import com.jascoffee.jascoffee.service.user.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController  // @Controller + @ResponseBody 결합
public class JoinController {

    private final JoinService joinService;

    public JoinController (JoinService joinService){
        this.joinService = joinService;
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

    @Autowired
    private JWTUtil jwtUtil;


    // POST 요청으로 account를 받아서 회원을 조회하고 반환
    @GetMapping("/user")
    public String getUserByAccount(@RequestHeader String access) {
        // account를 통해 DB에서 회원 정보 조회
        String account = jwtUtil.getAccount(access);

        return account;
    }

}
