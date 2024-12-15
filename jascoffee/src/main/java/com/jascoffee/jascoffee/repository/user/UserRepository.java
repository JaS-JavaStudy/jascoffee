package com.jascoffee.jascoffee.repository.user;

import com.jascoffee.jascoffee.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // account를 받아 DB 테이블에서 존재 여부 확인
    Boolean existsByAccount(String account);

    // account를 받아 DB 테이블에서 회원을 조회하는 메소드
    UserEntity findByAccount(String account);

    //mmId를 받아 DB 테이블에서 존재 여부 확인
    Boolean existsByMmid(String mmid);

    //회원 탈퇴 기능
    void deleteByAccount(String account);
}
