package com.enterprisex.wallsofwonder.auth.Services;



import com.enterprisex.wallsofwonder.auth.DTO.Requests.UserLoginRequest;
import com.enterprisex.wallsofwonder.auth.DTO.Requests.UserRegisterRequest;
import com.enterprisex.wallsofwonder.auth.DTO.UserDTO;
import com.enterprisex.wallsofwonder.auth.Enums.SignupType;
import com.enterprisex.wallsofwonder.auth.ServiceImpl.UserDetail;

import java.util.List;

public interface UserService {

    void userRegister(UserRegisterRequest request, SignupType signType, String otp);
    UserDTO userLogin(UserLoginRequest request);
    UserDTO adminLogin(UserLoginRequest request);
    UserDTO userProfile();
    void adminRegister(UserRegisterRequest request, SignupType signupType);
    List<UserDTO> getAllUsers();
    UserDetail validateToken();

    UserDTO userProfileUpdate(UserRegisterRequest body);
}
