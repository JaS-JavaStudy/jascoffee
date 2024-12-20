package com.jascoffee.jascoffee.controller.user;

import com.jascoffee.jascoffee.dto.user.PasswordUpdateDTO;
import com.jascoffee.jascoffee.dto.user.UserUpdateDTO;
import com.jascoffee.jascoffee.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateController {

    private final UserService userService;

    public UpdateController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping ("/update")
    public ResponseEntity<?> update(@RequestBody UserUpdateDTO request) {
        userService.updateUser(request);
        return ResponseEntity.ok().body("회원 정보가 수정되었습니다.");
    }

    @PutMapping("/password/update")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordUpdateDTO request) {
        userService.updatePassword(request);
        System.out.println("이거 print되면 수정 된거");
        return ResponseEntity.ok().body("비밀번호 변경되었습니다.");
    }
}
