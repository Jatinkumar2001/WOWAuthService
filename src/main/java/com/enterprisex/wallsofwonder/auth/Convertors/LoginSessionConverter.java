package com.enterprisex.wallsofwonder.auth.Convertors;

import com.enterprisex.wallsofwonder.auth.DTO.LoginSessionDTO;
import com.enterprisex.wallsofwonder.auth.Entities.LoginSessionEntity;
import com.enterprisex.wallsofwonder.auth.Entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginSessionConverter {
    public List<LoginSessionEntity> dtoToEntity(List<LoginSessionDTO> loginSession, UserEntity customer){
        return loginSession.stream().map(x-> dtoToEntity(x,customer)).collect(Collectors.toList());
    }

    public LoginSessionEntity dtoToEntity(LoginSessionDTO loginSession, UserEntity customer){
        LoginSessionEntity entity = new LoginSessionEntity();
        entity.setUserId(customer.getId());
        entity.setLoginType(loginSession.getLoginType());
        entity.setLoginSessionId(loginSession.getLoginSessionId());
        entity.setSessionExpiry(loginSession.getSessionExpiry());
        entity.setAuthToken(loginSession.getAuthToken());
        entity.setSocialAuth(loginSession.getSocialAuth());
        entity.setVerificationOtp(loginSession.getVerificationOtp());
        entity.setSuccessful(loginSession.isSuccessful());
        return entity;
    }

}
