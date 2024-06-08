package dev.christopherbell.thevoid.services;

import dev.christopherbell.thevoid.exceptions.AccountNotFoundException;
import dev.christopherbell.thevoid.exceptions.InvalidRequestException;
import dev.christopherbell.thevoid.exceptions.InvalidTokenException;
import dev.christopherbell.thevoid.models.contracts.user.VoidRequest;
import dev.christopherbell.thevoid.models.contracts.user.VoidResponse;
import dev.christopherbell.thevoid.models.db.InviteCodeEntity;
import dev.christopherbell.thevoid.models.domain.account.Account;
import dev.christopherbell.thevoid.services.messengers.AccountMessenger;
import dev.christopherbell.thevoid.services.messengers.InviteCodeMessenger;
import dev.christopherbell.thevoid.utils.ValidateUtil;
import dev.christopherbell.thevoid.utils.mappers.MapStructMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class InviteCodeService {

  private final AccountMessenger accountMessenger;
  private final InviteCodeMessenger inviteCodeMessenger;
  private final MapStructMapper mapStructMapper;
  private final PermissionsService permissionsService;

  /**
   *
   * @param clientId
   * @param loginToken
   * @param voidRequest
   * @return
   * @throws InvalidRequestException
   * @throws AccountNotFoundException
   * @throws InvalidTokenException
   */
  public VoidResponse generateInviteCode(String clientId, String loginToken, VoidRequest voidRequest)
      throws InvalidRequestException, AccountNotFoundException, InvalidTokenException {
    ValidateUtil.validateAccount(voidRequest);
    ValidateUtil.validateClientId(clientId);

    var account = Objects.requireNonNullElse(voidRequest.getAccount(), new Account());
    var accountId = Objects.requireNonNullElse(account.getId(), 0L);

    var isValidCreds = this.permissionsService.validateLoginToken(loginToken, accountId);
    if (!isValidCreds) {
      throw new InvalidTokenException("Invalid token.");
    } else {
      // Build a template success response
      var accountEntity = this.accountMessenger.getAccountEntityById(accountId);
      var inviteCodeEntity = new InviteCodeEntity();
      inviteCodeEntity.setAccountEntity(accountEntity);
      inviteCodeEntity.setCode(UUID.randomUUID().toString());
      // Save the invite Code
      this.inviteCodeMessenger.saveInviteCode(inviteCodeEntity);

      return VoidResponse.builder()
          .inviteCode(mapStructMapper.mapToInviteCode(inviteCodeEntity))
          .build();
    }
  }
}
