package com.jascoffee.jascoffee.dto.user;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
public class UserDTO {
    private String account;
    private String name;
    private String mmid;
    private String fund;

    public UserDTO(String account, String name, String mmid, String fund) {
        this.account = account;
        this.name = name;
        this.mmid = mmid;
        this.fund = fund;
    }
}
