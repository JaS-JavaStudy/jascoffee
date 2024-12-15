package com.jascoffee.jascoffee.service.user;

import com.jascoffee.jascoffee.repository.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void deleteUser(String account) {
        // 계정 존재 여부 확인
        if (!userRepository.existsByAccount(account)) {
            System.out.println("deleteUser에 if문에 들어왔습니다.");
            throw new IllegalArgumentException("해당 계정이 존재하지 않습니다.");
        }
        // 계정 삭제
        System.out.println("deleteUser에 if문 밖입니다.");
        userRepository.deleteByAccount(account);
        System.out.println("UserRepository에서 삭제 기능 하고 오는 길");
    }
}
