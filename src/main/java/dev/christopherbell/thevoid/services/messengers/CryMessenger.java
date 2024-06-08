package dev.christopherbell.thevoid.services.messengers;

import dev.christopherbell.thevoid.models.db.CryEntity;
import dev.christopherbell.thevoid.models.db.account.AccountEntity;
import dev.christopherbell.thevoid.repositories.CryRepository;
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
