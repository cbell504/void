package com.thevoid.api.repositories;

import com.thevoid.api.models.db.account.AccountDetailsEntity;
import com.thevoid.api.models.db.account.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountDetailsRepository extends JpaRepository<AccountDetailsEntity, Long> {

}
