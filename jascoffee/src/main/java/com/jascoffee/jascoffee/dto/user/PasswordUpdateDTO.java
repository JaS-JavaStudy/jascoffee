package com.jascoffee.jascoffee.dto.user;

import lombok.Data;

@Data
public class PasswordUpdateDTO {
    private String currentPassword;
    private String newPassword;
}
