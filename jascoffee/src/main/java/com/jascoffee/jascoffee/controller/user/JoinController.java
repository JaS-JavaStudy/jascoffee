package com.jascoffee.jascoffee.controller.user;

import com.jascoffee.jascoffee.dto.user.JoinDTO;
import com.jascoffee.jascoffee.jwt.JWTUtil;
import com.jascoffee.jascoffee.service.user.JoinService;
import com.jascoffee.jascoffee.repository.user.UserRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class JoinController {

    private final JoinService joinService;
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;  // UserRepository 추가

    // 생성자에서 UserRepository 주입
    public JoinController(JoinService joinService, JWTUtil jwtUtil, UserRepository userRepository) {
        this.joinService = joinService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    // 회원 가입 처리
    @PostMapping("/join")
    public ResponseEntity<?> joinProcess(@RequestBody JoinDTO joinDTO) {
        joinService.joinProcess(joinDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원 가입이 완료되었습니다.");
    }

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
        boolean isStaff = jwtUtil.getIsStaff(access);
        return isStaff;
    }

    // account가 존재하는지 확인하는 API
    @GetMapping("/join/account")
    public ResponseEntity<String> checkAccount(@RequestParam String account) {

        // account와 mmid 존재 여부 확인
        boolean isAccountExist = userRepository.existsByAccount(account);

        if (isAccountExist) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("이미 존재하는 계정입니다.");
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body("사용 가능한 계정입니다.");
    }
    // mmid가 존재하는지 확인하는 API
    @GetMapping("/join/mmid")
    public ResponseEntity<String> checkMmid(@RequestParam String mmid) {

        // mmid 존재 여부 확인
        boolean isMmidExist = userRepository.existsByMmid(mmid);

        if (isMmidExist) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("이미 존재하는 MMID입니다.");
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body("사용 가능한 MMID입니다.");
    }
}
