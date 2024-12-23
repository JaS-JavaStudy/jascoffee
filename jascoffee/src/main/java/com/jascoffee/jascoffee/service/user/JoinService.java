package com.jascoffee.jascoffee.service.user;

import com.jascoffee.jascoffee.dto.user.JoinDTO;
import com.jascoffee.jascoffee.dto.user.UserDTO;
import com.jascoffee.jascoffee.entity.user.UserEntity;
import com.jascoffee.jascoffee.repository.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        String bank = joinDTO.getBank();
        String fund = joinDTO.getFund();
        Boolean isStaff = joinDTO.getIsStaff();

        // 비밀번호가 null이거나 빈 문자열인 경우 예외 처리
        if (joinDTO.isPasswordEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        // 이미 존재하는 계정 체크
        boolean isExist = userRepository.existsByAccount(account);
        if (isExist) {
            throw new IllegalArgumentException("Account already exists");
        }

        // 이미 존재하는 mmId 체크
        boolean isExistMm = userRepository.existsByMmid(mmid);
        if (isExistMm) {
            throw new IllegalArgumentException("MMID already exists");
        }

        // 사용자 엔티티 생성 및 정보 설정
        UserEntity data = new UserEntity();
        data.setAccount(account);
        data.setPassword(bCryptPasswordEncoder.encode(password)); // 비밀번호 암호화
        data.setName(name);
        data.setMmid(mmid);
        data.setBank(bank);
        data.setFund(fund);
        data.setIsStaff(isStaff);

        // 데이터베이스에 저장
        userRepository.save(data);
    }

    @Transactional
    public void deleteUserByAccount(String account) {

        userRepository.deleteByAccount(account);
    }

    public UserDTO getUserByAccount(String account){
        UserEntity user = userRepository.findByAccount(account)
                .orElseThrow(() -> new RuntimeException("없는 회원 입니다."));

        return new UserDTO(user.getAccount(),user.getName(),user.getMmid(),user.getBank(),user.getFund());
    }
}
