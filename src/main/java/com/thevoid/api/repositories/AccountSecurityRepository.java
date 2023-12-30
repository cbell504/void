package com.thevoid.api.repositories;

import com.thevoid.api.models.db.account.AccountDetailsEntity;
import com.thevoid.api.models.db.account.AccountEntity;
import com.thevoid.api.models.db.account.AccountSecurityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountSecurityRepository extends JpaRepository<AccountSecurityEntity, Long> {
    Optional<AccountSecurityEntity> findByEmail(String email);
}
