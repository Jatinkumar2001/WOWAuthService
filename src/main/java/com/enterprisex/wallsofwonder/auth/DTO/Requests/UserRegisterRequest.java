package com.enterprisex.wallsofwonder.auth.DTO.Requests;

import com.enterprisex.wallsofwonder.auth.Enums.Gender;
import com.enterprisex.wallsofwonder.auth.Enums.Role;
import com.enterprisex.wallsofwonder.auth.Enums.SignupType;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserRegisterRequest {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Gender gender;
    private Date dateOfBirth;
    private SignupType signupType;
    private String profileStatus;
    private Boolean active;
    private String inActiveReason;
    private Boolean isCodAvailable;
    private Role role;
    private String password;
//    private List<UserAddressRequest> addresses;

}
