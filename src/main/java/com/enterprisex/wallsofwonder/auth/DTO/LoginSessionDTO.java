package com.enterprisex.wallsofwonder.auth.DTO;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class LoginSessionDTO implements Serializable {
    private Long id;
    private String loginType;
    private String loginSessionId;
    private Timestamp sessionExpiry;
    private String authToken;
    private String socialAuth;
    private String verificationOtp;
    private boolean isSuccessful;
    private Long userId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp currentTime;
}
