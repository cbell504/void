package com.thevoid.api.services.messengers;

import com.thevoid.api.models.db.CryEntity;
import com.thevoid.api.models.db.account.AccountEntity;
import com.thevoid.api.models.domain.Cry;
import com.thevoid.api.repositories.CryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CryMessenger {
    private final CryRepository cryRepository;

    @Autowired
    public CryMessenger(CryRepository cryRepository) {
        this.cryRepository = cryRepository;
    }

    public List<CryEntity> findByAccountEntity(AccountEntity accountEntity) {
        return this.cryRepository.findByAccountEntity(accountEntity);
    }

    public void saveCryRepository(CryEntity cryEntity) {
        this.cryRepository.save(cryEntity);
    }
}
