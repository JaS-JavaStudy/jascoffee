package com.jascoffee.jascoffee.controller.user;

import com.jascoffee.jascoffee.dto.user.UserDTO;
import com.jascoffee.jascoffee.entity.user.UserEntity;
import com.jascoffee.jascoffee.jwt.JWTUtil;
import com.jascoffee.jascoffee.repository.user.UserRepository;
import com.jascoffee.jascoffee.service.user.JoinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private JoinService userService;
    private JWTUtil jwtUtil;
    private UserRepository userRepository;

    public UserController(JoinService userService, JWTUtil jwtUtil, UserRepository userRepository) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestHeader("access") String token) {

        // JWT에서 account 추출
        String account = jwtUtil.getAccount(token);

        // 탈퇴 처리
        try {
            userService.deleteUserByAccount(account);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while deleting user: " + e.getMessage());
        }
    }

    //아 id로 할려다가 id로 받아올려면 jwt관련해서 너무 많은 코드를 건드려야해서 안전하게 account로 했습니다.
    // 어차피 account도 중복 제거 해서 id랑 마찬가지로 unique하긴 합니다.!
    @GetMapping("/{account}")
    public ResponseEntity<UserDTO> getUserByAccount(@PathVariable("account") String account) { // 명시적으로 "id" 지정
        UserDTO user = userService.getUserByAccount(account);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        try {
            List<UserEntity> users = userRepository.findAll(); // 모든 유저를 가져옴
            return ResponseEntity.ok(users); // HTTP 200과 함께 응답
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // 오류 발생 시 500 에러 응답
        }
    }
}
