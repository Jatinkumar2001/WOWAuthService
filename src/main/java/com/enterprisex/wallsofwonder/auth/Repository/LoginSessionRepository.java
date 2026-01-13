package com.enterprisex.wallsofwonder.auth.Repository;

import com.enterprisex.wallsofwonder.auth.Entities.LoginSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LoginSessionRepository extends JpaRepository<LoginSessionEntity,Long> {
    @Query(value = "select * from usrmgmt.login_session where user_id =?1 order by created_at desc limit 1",nativeQuery = true)
    LoginSessionEntity findByUserId(Long id);
}
