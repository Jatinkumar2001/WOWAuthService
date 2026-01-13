package com.enterprisex.wallsofwonder.auth.Convertors;

import com.enterprisex.wallsofwonder.auth.DTO.Requests.UserRegisterRequest;
import com.enterprisex.wallsofwonder.auth.DTO.UserDTO;
import com.enterprisex.wallsofwonder.auth.Entities.UserDetailEntity;
import com.enterprisex.wallsofwonder.auth.Entities.UserEntity;
import com.enterprisex.wallsofwonder.auth.Enums.Gender;
import com.enterprisex.wallsofwonder.auth.Enums.ProfileStatus;
import com.enterprisex.wallsofwonder.auth.Enums.SignupType;
import com.enterprisex.wallsofwonder.auth.ServiceImpl.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserConverter {

    @Autowired
    private LoginSessionConverter loginSessionConverter;

//    @Autowired
//    private AddressConvertor addressConvertor;


    public UserDetailEntity entityToDto(UserEntity customer, boolean isUserDetails) throws UsernameNotFoundException {
        UserDetailEntity dto = new UserDetailEntity();
        dto.setId(customer.getId());
        dto.setPhoneNumber(customer.getPhone());
        return dto;
    }


    public UserDTO entityToDto(UserEntity entity, String token) {

        if (entity == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setPhone(entity.getPhone());
        dto.setProfileStatus(entity.getProfileStatus());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setGender(Gender.valueOf(entity.getGender()));
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setEmail(entity.getEmail());
        dto.setActive(entity.getActive());
        dto.setIsCodAvailable(entity.getIsCodAvailable());
        dto.setSignupType(SignupType.valueOf(entity.getSignupType()));
        dto.setToken(token);
        return dto;
    }





    public UserDetail entityToUserDetail(UserEntity authUsersEntity) {
        UserDetail userDetails = new UserDetail();
        userDetails.setUserName(authUsersEntity.getPhone());
        userDetails.setId(authUsersEntity.getId());
        userDetails.setRole(authUsersEntity.getRole());
        userDetails.setPassword(authUsersEntity.getPassword());
        return userDetails;
    }


    public UserDTO userEntityToDto(UserEntity customer) {
        if (customer==null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(customer.getId());
        dto.setPhone(customer.getPhone());
        dto.setEmail(customer.getEmail());
        dto.setSignupType(SignupType.valueOf(customer.getSignupType()));
        dto.setProfileStatus(customer.getProfileStatus());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setGender(Gender.valueOf(customer.getGender()));
        dto.setDateOfBirth(customer.getDateOfBirth());
//        dto.setAddresses(addressConvertor.entityToDto(customer.getAddresses()));

        return dto;
    }
    public UserEntity requestToEntity(UserRegisterRequest request) {
        if (request==null) return null;
        UserEntity entity = new UserEntity();
        entity.setPhone(request.getPhone());
        entity.setSignupType(request.getSignupType().name());
        entity.setEmail(request.getEmail());
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setGender(request.getGender().name());
        entity.setDateOfBirth(request.getDateOfBirth());
        entity.setRole(request.getRole().name());
        entity.setPassword(request.getPassword());
        entity.setProfileStatus(ProfileStatus.PENDING.toString());
//        if(request.getAddresses()!=null)
//            entity.setAddresses(addressConvertor.entityToDto(request.getAddresses(),entity));

        return entity;
    }



    public List<UserDTO> userEntityToDto(List<UserEntity> userEntities) {
        if (userEntities==null) return new ArrayList<>();
        return userEntities.stream().map(this::userEntityToDto).collect(Collectors.toList());
    }

    public UserEntity userEntityToAdminDto(UserRegisterRequest request) {

        if (request == null) return null;
        UserEntity entity = new UserEntity();
        entity.setPhone(request.getPhone());
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setGender(request.getGender().name());
        entity.setDateOfBirth(request.getDateOfBirth());
        entity.setEmail(request.getEmail());
        entity.setProfileStatus(request.getProfileStatus());
        entity.setSignupType(request.getSignupType().name());
        entity.setRole(request.getRole().name());
        entity.setPassword(request.getPassword());
        return entity;
    }
}
