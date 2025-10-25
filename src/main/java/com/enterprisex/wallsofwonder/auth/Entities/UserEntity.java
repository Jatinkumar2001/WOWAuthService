package com.enterprisex.wallsofwonder.auth.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "users",schema = "usrmgmt")
@DynamicInsert
public class UserEntity extends AbstractEntity implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "users_id_seq")
    @SequenceGenerator(name = "users_id_seq", sequenceName = "usrmgmt.users_id_seq", allocationSize = 1)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String gender;
    private Date dateOfBirth;
    private String signupType;
    private String profileStatus;
    private Boolean active;
    private String inActiveReason;
    private Boolean isCodAvailable;
    private String  role;
    private String password;

//    @JsonIgnore
//    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
//    private List<UserAddressEntity> addresses;


}
