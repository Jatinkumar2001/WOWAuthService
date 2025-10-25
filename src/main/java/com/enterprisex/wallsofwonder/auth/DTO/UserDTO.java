package com.enterprisex.wallsofwonder.auth.DTO;

import com.enterprisex.wallsofwonder.auth.Enums.Gender;
import com.enterprisex.wallsofwonder.auth.Enums.Role;
import com.enterprisex.wallsofwonder.auth.Enums.SignupType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class UserDTO implements Serializable {

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
    private String token;
    private Set<LoginSessionDTO> loginSession;
//    private List<UserAddressDTO> addresses;

}
