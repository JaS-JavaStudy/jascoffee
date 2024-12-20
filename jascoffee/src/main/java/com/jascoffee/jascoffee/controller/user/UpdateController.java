package com.jascoffee.jascoffee.controller.user;

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
        System.out.println("회원정보 수정 디버깅 용");
        return ResponseEntity.ok().build();
    }
}
