package com.enterprisex.wallsofwonder.auth.Repository;

import com.enterprisex.wallsofwonder.auth.Entities.LoginSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginSessionRepository extends JpaRepository<LoginSessionEntity,Long> {
}
