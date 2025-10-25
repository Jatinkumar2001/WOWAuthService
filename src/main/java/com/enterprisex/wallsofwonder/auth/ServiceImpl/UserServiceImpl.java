package com.enterprisex.wallsofwonder.auth.ServiceImpl;


import com.enterprisex.wallsofwonder.auth.Convertors.LoginSessionConverter;
import com.enterprisex.wallsofwonder.auth.Convertors.UserConverter;
import com.enterprisex.wallsofwonder.auth.DTO.LoginSessionDTO;
import com.enterprisex.wallsofwonder.auth.DTO.Requests.UserLoginRequest;
import com.enterprisex.wallsofwonder.auth.DTO.Requests.UserRegisterRequest;
import com.enterprisex.wallsofwonder.auth.DTO.UserDTO;
import com.enterprisex.wallsofwonder.auth.Entities.LoginSessionEntity;
import com.enterprisex.wallsofwonder.auth.Entities.UserEntity;
import com.enterprisex.wallsofwonder.auth.Enums.ProfileStatus;
import com.enterprisex.wallsofwonder.auth.Enums.Role;
import com.enterprisex.wallsofwonder.auth.Enums.SignupType;
import com.enterprisex.wallsofwonder.auth.Repository.LoginSessionRepository;
import com.enterprisex.wallsofwonder.auth.Repository.UserRepository;
import com.enterprisex.wallsofwonder.auth.Security.JwtUtil;
import com.enterprisex.wallsofwonder.auth.Services.UserService;
import com.enterprisex.wallsofwonder.auth.Util.AuthUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;



    @Autowired
    private LoginSessionRepository loginSessionRepository;

    @Autowired
    private LoginSessionConverter loginSessionConverter;


    @Transactional
    @Override
    public void userRegister(UserRegisterRequest request, SignupType signType, String otp) {

        UserEntity userEntity = userRepository.findByMobileNumberAndRole(request.getPhone(),Role.USER.name());
        if(userEntity==null){
            request.setRole(Role.USER);
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            request.setPassword(encodedPassword);
            request.setSignupType(signType);
//            LoginSessionDTO loginSessionDto = new LoginSessionDTO();
//            loginSessionDto.setVerificationOtp(otp);
            userEntity = userConverter.requestToEntity(request);
            userRepository.save(userEntity);

        }else {
            throw new RuntimeException("Phone Number Already Exist");
        }

    }

    @Override
    public UserDTO userLogin(UserLoginRequest request) {
        try {
            UserEntity userEntity = userRepository.findByMobileNumberAndRole(request.getPhone(), Role.USER.name());
            if(userEntity!=null){
                boolean matches = passwordEncoder.matches(request.getPassword(), userEntity.getPassword());
                if (matches) {
                    // login success
                    final Authentication authentication =
                            authenticationManager.authenticate(
                                    new UsernamePasswordAuthenticationToken(userEntity.getId(), request.getPassword())
                            );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                    final UserEntity user = userDetailsService.loadUserByUsername(String.valueOf(request.getPhone()));
                    String token = jwtUtil.generateToken(userConverter.entityToUserDetail(userEntity));
                    userEntity = userRepository.findByMobileNumberAndRole(request.getPhone(), Role.USER.name());
                    LoginSessionEntity loginSession = loginSessionConverter.dtoToEntity(new LoginSessionDTO(), userEntity);
                    loginSessionRepository.save(loginSession);
                    return userConverter.entityToDto(userEntity,token);
                } else {
                    // login failed
                    throw new RuntimeException("Invalid Credentials");
                }
            }

            throw new RuntimeException("Invalid Credentials");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }




    @PreAuthorize("hasRole('USER')")
    @Override
    public UserDTO userProfile() {
        UserDetail user = AuthUtil.getCurrentUser();
        if (user == null) throw new UsernameNotFoundException("User Not Found");
        UserEntity userEntity = userRepository.findByMobileNumberAndRole(user.getPhoneNumber(),Role.USER.name());
        return userConverter.userEntityToDto(userEntity);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<UserDTO> getAllUsers() {
        List<UserEntity> userEntities = userRepository.getAllVendorsAndUsers(Role.USER.name());
        return userConverter.userEntityToDto(userEntities);
    }

    @Override
    public void adminRegister(UserRegisterRequest request, SignupType signupType) {
        UserEntity userEntity = userRepository.findByMobileNumberAndRole(request.getPhone(),Role.ADMIN.name());

        if(userEntity==null){
            request.setRole(Role.ADMIN);
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            request.setPassword(encodedPassword);
            request.setProfileStatus(ProfileStatus.PENDING.name());
            request.setSignupType(signupType);
            userEntity = userConverter.userEntityToAdminDto(request);
            userRepository.save(userEntity);
        }else {
            throw new RuntimeException("Phone Number Already Exist");
        }
    }

    @Override
    public UserDTO adminLogin(UserLoginRequest request) {
        try {
            UserEntity userEntity = userRepository.findByMobileNumberAndRole(request.getPhone(), Role.ADMIN.name());
            if(userEntity!=null){
                boolean matches = passwordEncoder.matches(request.getPassword(), userEntity.getPassword());
                if (matches) {
                    // login success
                    UserDetail userDetails =userConverter.entityToUserDetail(userEntity);
                    final Authentication authentication =
                            authenticationManager.authenticate(
                                    new UsernamePasswordAuthenticationToken(userEntity.getId(), request.getPassword())
                            );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                    final UserEntity user = userDetailsService.loadUserByUsername(String.valueOf(request.getPhone()));
                    String token = jwtUtil.generateToken(userDetails);
                    userEntity = userRepository.findByMobileNumberAndRole(request.getPhone(), Role.ADMIN.name());
                    LoginSessionEntity loginSession = loginSessionConverter.dtoToEntity(new LoginSessionDTO(), userEntity);
                    loginSessionRepository.save(loginSession);
                    return userConverter.entityToDto(userEntity,token);
                } else {
                    // login failed
                    throw new RuntimeException("Invalid Credentials");
                }
            }

            throw new RuntimeException("Your Profile in Pending");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }


}
