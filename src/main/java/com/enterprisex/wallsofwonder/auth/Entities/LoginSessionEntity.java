package com.enterprisex.wallsofwonder.auth.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;

import java.io.Serializable;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "login_session",schema = "usrmgmt")
@DynamicInsert
public class LoginSessionEntity extends AbstractEntity implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "login_session_id_seq")
    @SequenceGenerator(name = "login_session_id_seq", sequenceName = "usrmgmt.login_session_id_seq", allocationSize = 1)
    private Long id;
    private Long userId;

    @Column(name = "login_type")
    private String loginType;

    @Column(name = "login_session_id")
    private String loginSessionId;

    @Column(name = "session_expiry")
    private Timestamp sessionExpiry;

    @Column(name = "auth_token")
    private String authToken;

    @Column(name = "social_auth")
    private String socialAuth;

    @Column(name = "verification_otp")
    private String verificationOtp;

    @Column(name = "is_successful")
    private boolean isSuccessful;

    @Transient
    @Column(name = "current_time",insertable = false,updatable = false)
    private Timestamp currentTime;
}
