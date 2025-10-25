package com.enterprisex.wallsofwonder.auth.ServiceImpl;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@Data
public class UserDetail implements UserDetails {

	private static final String ROLE_PREFIX = "ROLE_";

    private String userName ;
    private String phoneNumber;
    private String otp;
    private String token;
    private String role;
    private Long id;
    private String password;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.toUpperCase()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }


}
