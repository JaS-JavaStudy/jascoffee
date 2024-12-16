package com.jascoffee.jascoffee.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users") // 테이블 이름 지정
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID") // 컬럼 이름 지정
    private Long userID;

    @Column(name = "account")
    private String account;

    @Column(name = "Password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "MMID")
    private String mmid;

    @Column(name = "fund")
    private String fund;

    @Column(name = "isStaff")
    private Boolean isStaff;
}
