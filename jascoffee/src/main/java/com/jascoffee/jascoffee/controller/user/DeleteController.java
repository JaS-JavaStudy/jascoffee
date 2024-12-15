package com.jascoffee.jascoffee.controller.user;

import com.jascoffee.jascoffee.jwt.JWTUtil;
import com.jascoffee.jascoffee.service.user.JoinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class DeleteController {

    private JoinService userService;
    private JWTUtil jwtUtil;

    public DeleteController(JoinService userService, JWTUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestHeader("Authorization") String token) {
        // Bearer prefix 제거
        System.out.println(token);
        String jwtToken = token.replace("Bearer ", "");
        System.out.println(jwtToken);
        // JWT에서 account 추출
        String account = jwtUtil.getAccount(jwtToken);
        System.out.println(account);
        // 탈퇴 처리
        try {
            userService.deleteUserByAccount(account);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while deleting user: " + e.getMessage());
        }
    }
}
