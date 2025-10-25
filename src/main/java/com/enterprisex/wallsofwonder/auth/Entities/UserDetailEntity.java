package com.enterprisex.wallsofwonder.auth.Entities;

import com.enterprisex.wallsofwonder.auth.Enums.Role;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

@Service
@Data
public class UserDetailEntity implements UserDetails , Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String ROLE_PREFIX = "ROLE_";

    private String userName ;
    private String  phoneNumber;
    private String otp;

    private String token;

    private Role role;

    private Boolean isVerified;

    private Long id;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(ROLE_PREFIX + role.name().toUpperCase()));

    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }
}
