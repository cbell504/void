package dev.christopherbell.thevoid.services;

import dev.christopherbell.thevoid.exceptions.VoidAccountNotFoundException;
import dev.christopherbell.thevoid.exceptions.VoidInvalidTokenException;
import dev.christopherbell.thevoid.models.contracts.user.VoidRequest;
import dev.christopherbell.thevoid.models.contracts.user.VoidResponse;
import dev.christopherbell.thevoid.models.domain.Cry;
import dev.christopherbell.thevoid.services.messengers.AccountMessenger;
import dev.christopherbell.thevoid.services.messengers.CryMessenger;
import dev.christopherbell.thevoid.utils.ResponseUtil;
import dev.christopherbell.thevoid.utils.mappers.MapStructMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@Service
public class CryService {

  private final AccountMessenger accountMessenger;
  private final CryMessenger cryMessenger;
  private final MapStructMapper mapStructMapper;
  private final PermissionsService permissionsService;

  @Autowired
  public CryService(AccountMessenger accountMessenger,
      CryMessenger cryMessenger,
      MapStructMapper mapStructMapper,
      PermissionsService permissionsService) {
    this.accountMessenger = accountMessenger;
    this.cryMessenger = cryMessenger;
    this.mapStructMapper = mapStructMapper;
    this.permissionsService = permissionsService;
  }

  public VoidResponse createCry(String loginToken, Long accountId, VoidRequest voidRequest)
      throws VoidAccountNotFoundException, VoidInvalidTokenException {
    log.info("Request for all cries for given accountId: " + accountId);

    // Validate the token for the user
    if (!this.permissionsService.validateLoginToken(loginToken, accountId)) {
      throw new VoidAccountNotFoundException();
    }
    var cry = Objects.requireNonNullElse(voidRequest.getCry(), new Cry());

    // Convert Cry to CryEntity
    var cryEntity = this.mapStructMapper.mapToCryEntity(cry);
    var accountEntity = this.accountMessenger.getAccountEntityById(accountId);
    cryEntity.setAccountEntity(accountEntity);
    this.cryMessenger.saveCryRepository(cryEntity);

    return ResponseUtil.buildSuccessfulResponse(HttpStatus.CREATED);
  }

  public VoidResponse getAllCriesByAccountId(Long accountId) throws VoidAccountNotFoundException {
    //TODO: Write a test to see if this will prevent a DB attack
    //var username = Jsoup.clean(dirtyUsername, Safelist.basic());
    log.info("Request for all cries for given accountId: " + accountId);

    var accountEntity = this.accountMessenger.getAccountEntityById(accountId);
    var cryEntities = this.cryMessenger.findByAccountEntity(accountEntity);
    var cries = new ArrayList<Cry>();
    for (var cryEntity : cryEntities) {
      var cry = this.mapStructMapper.mapToCry(cryEntity);
      cries.add(cry);
    }

    var response = ResponseUtil.buildSuccessfulResponse(HttpStatus.OK);
    response.setCries(cries);

    return response;
  }

}
