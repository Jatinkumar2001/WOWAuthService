package com.enterprisex.wallsofwonder.auth.Util;

import com.enterprisex.wallsofwonder.auth.ServiceImpl.UserDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;
import java.util.stream.Collectors;

public class AuthUtil {

    public static UserDetail getCurrentUser() {
        return (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    public static String getCurrentUserRoles(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Set<String> roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority) // extract "USER", "ADMIN", etc.
                .collect(Collectors.toSet());
        String  r = "";
        for(String role :roles){
            r = role;
            break;
        }
        return r;
    }
}
