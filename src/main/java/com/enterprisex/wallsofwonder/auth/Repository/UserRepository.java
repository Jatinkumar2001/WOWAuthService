package com.enterprisex.wallsofwonder.auth.Repository;


import com.enterprisex.wallsofwonder.auth.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {


    @Query(value="select * from usrmgmt.users u where u.id = :mobileNumber",nativeQuery = true)
    Optional<UserEntity> findByUserName(@Param("mobileNumber")Long username);

    @Query(value="select * from usrmgmt.users u where u.phone = ?1  and u.role = ?2 and profile_status = 'COMPLETED'  ",nativeQuery = true)
    UserEntity findByMobileNumberAndRole(String phone, String name);
    @Query(value="select * from usrmgmt.users u where u.phone = ?1  and u.role = 'USER'",nativeQuery = true)
    UserEntity findByMobileNumber(String phone);
    @Query(value="select * from usrmgmt.users u where  u.role = ?1",nativeQuery = true)
    List<UserEntity> getAllVendorsAndUsers(String role);
}
