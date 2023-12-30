package com.thevoid.api.repositories;

import com.thevoid.api.models.db.CryEntity;
import com.thevoid.api.models.db.account.AccountEntity;
import com.thevoid.api.models.domain.Cry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CryRepository extends JpaRepository<CryEntity, Long> {
    List<CryEntity> findByAccountEntity(AccountEntity accountEntity);
}