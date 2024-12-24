package com.jascoffee.jascoffee.repository.user;

import com.jascoffee.jascoffee.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // account를 받아 DB 테이블에서 존재 여부 확인
    boolean existsByAccount(String account);

    // account를 받아 DB 테이블에서 회원을 조회하는 메소드
    Optional<UserEntity> findByAccount(String account);

    List<UserEntity> findAll();

    //mmId를 받아 DB 테이블에서 존재 여부 확인
    boolean existsByMmid(String mmid);

    void deleteByAccount(String account);
}
