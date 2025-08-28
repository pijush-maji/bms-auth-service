package com.bms.auth.dto;

import lombok.Data;

@Data
public class LoginReq {
    private String email;
    private String password;
}
