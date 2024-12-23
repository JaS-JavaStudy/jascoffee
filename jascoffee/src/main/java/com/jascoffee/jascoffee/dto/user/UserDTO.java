package com.jascoffee.jascoffee.dto.user;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
public class UserDTO {
    private String account;
    private String name;
    private String mmid;
    private String bank;
    private String fund;

    public UserDTO(String account, String name, String mmid, String bank,String fund) {
        this.account = account;
        this.name = name;
        this.mmid = mmid;
        this.bank = bank;
        this.fund = fund;
    }
}
