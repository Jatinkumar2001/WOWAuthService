package com.enterprisex.wallsofwonder.auth.ServiceImpl;


import com.enterprisex.wallsofwonder.auth.Convertors.UserConverter;
import com.enterprisex.wallsofwonder.auth.Entities.UserEntity;
import com.enterprisex.wallsofwonder.auth.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Optional<UserEntity> customer = userRepository.findByUserName(Long.parseLong(username));
            if (customer.isEmpty()) throw  new UsernameNotFoundException("User Not Found");
            UserEntity authUsersEntity = customer.get();
            return userConverter.entityToUserDetail(authUsersEntity);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

}
