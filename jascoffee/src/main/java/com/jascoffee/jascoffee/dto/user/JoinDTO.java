package com.jascoffee.jascoffee.dto.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JoinDTO {

    private String account;    // 계정 이름

    private String password;   // 비밀번호

    private String name;       // 사용자 이름

    private String mmid;       // 사용자 고유 ID

    private String fund;       // 자금 정보

    private Boolean isStaff;   // 직원 여부


    // 비밀번호 검증 추가
    public boolean isPasswordEmpty() {
        return password == null || password.trim().isEmpty();
    }
}
