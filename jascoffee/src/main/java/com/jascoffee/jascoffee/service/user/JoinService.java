package com.jascoffee.jascoffee.service.user;

import com.jascoffee.jascoffee.dto.user.JoinDTO;
import com.jascoffee.jascoffee.entity.user.UserEntity;
import com.jascoffee.jascoffee.repository.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(JoinDTO joinDTO) {

        String account = joinDTO.getAccount();
        String password = joinDTO.getPassword();
        String name = joinDTO.getName();
        String mmid = joinDTO.getMmid();
        String fund = joinDTO.getFund();
        Boolean isStaff = joinDTO.getIsStaff();

        // 디버깅 코드 추가
        System.out.println("Password: " + password); // 비밀번호 값 출력
        System.out.println("Account: " + account);   // 계정 값 출력
        System.out.println("Name: " + name);         // 이름 값 출력

        // 비밀번호가 null이거나 빈 문자열인 경우 예외 처리
        if (joinDTO.isPasswordEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        // 이미 존재하는 계정 체크
        Boolean isExist = userRepository.existsByAccount(account);
        if (isExist) {
            throw new IllegalArgumentException("Account already exists");
        }

        // 사용자 엔티티 생성 및 정보 설정
        UserEntity data = new UserEntity();
        data.setAccount(account);
        data.setPassword(bCryptPasswordEncoder.encode(password)); // 비밀번호 암호화
        data.setName(name);
        data.setMmid(mmid);
        data.setFund(fund);
        data.setIsStaff(isStaff);

        // 데이터베이스에 저장
        userRepository.save(data);
    }
}
