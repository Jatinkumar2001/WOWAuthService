package com.enterprisex.wallsofwonder.auth.DTO.Requests;

import lombok.Data;

@Data
public class UserLoginRequest {

    private String phone;
    private String password;
    private String otp;
}
