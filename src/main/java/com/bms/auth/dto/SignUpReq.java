package com.bms.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpReq {

    @NotBlank(message = "Name can't be blank")
    private String name;
    @Email(message = "Not a valid email")
    @NotBlank(message = "Email can't be blank")
    private String email;
    @NotBlank(message = "Password can't be blank")
    private String password;
    @NotBlank(message = "User role can't be blank")
    private String userRole;

}
