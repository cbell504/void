package dev.christopherbell.thevoid.services;

import dev.christopherbell.thevoid.configs.Constants;
import dev.christopherbell.thevoid.exceptions.VoidAccountNotFoundException;
import dev.christopherbell.thevoid.exceptions.VoidInvalidRequestException;
import dev.christopherbell.thevoid.exceptions.VoidInvalidTokenException;
import dev.christopherbell.thevoid.models.contracts.user.VoidRequest;
import dev.christopherbell.thevoid.models.contracts.user.VoidResponse;
import dev.christopherbell.thevoid.models.db.InviteCodeEntity;
import dev.christopherbell.thevoid.models.domain.account.Account;
import dev.christopherbell.thevoid.models.contracts.global.Message;
import dev.christopherbell.thevoid.services.messengers.AccountMessenger;
import dev.christopherbell.thevoid.services.messengers.InviteCodeMessenger;
import dev.christopherbell.thevoid.utils.ResponseUtil;
import dev.christopherbell.thevoid.utils.ValidateUtil;
import dev.christopherbell.thevoid.utils.mappers.MapStructMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class InviteCodeService {

  private final AccountMessenger accountMessenger;
  private final InviteCodeMessenger inviteCodeMessenger;
  private final MapStructMapper mapStructMapper;
  private final PermissionsService permissionsService;

  @Autowired
  public InviteCodeService(AccountMessenger accountMessenger,
      InviteCodeMessenger inviteCodeMessenger,
      MapStructMapper mapStructMapper,
      PermissionsService permissionsService) {
    this.accountMessenger = accountMessenger;
    this.inviteCodeMessenger = inviteCodeMessenger;
    this.mapStructMapper = mapStructMapper;
    this.permissionsService = permissionsService;
  }

  public VoidResponse generateInviteCode(String clientId, String loginToken, VoidRequest voidRequest)
      throws VoidInvalidRequestException, VoidAccountNotFoundException, VoidInvalidTokenException {
    ValidateUtil.validateAccount(voidRequest);
    ValidateUtil.validateClientId(clientId);

    var account = Objects.requireNonNullElse(voidRequest.getAccount(), new Account());
    var accountId = Objects.requireNonNullElse(account.getId(), 0L);

    var isValidCreds = this.permissionsService.validateLoginToken(loginToken, accountId);
    var response = new VoidResponse();
    if (isValidCreds) {
      // Build a template success response
      response = ResponseUtil.buildSuccessfulResponse(HttpStatus.CREATED);
      var accountEntity = this.accountMessenger.getAccountEntityById(accountId);
      var inviteCodeEntity = new InviteCodeEntity();
      inviteCodeEntity.setAccountEntity(accountEntity);
      inviteCodeEntity.setCode(UUID.randomUUID().toString());
      // Save the invite Code
      this.inviteCodeMessenger.saveInviteCode(inviteCodeEntity);
      var inviteCode = this.mapStructMapper.mapToInviteCode(inviteCodeEntity);
      response.setInviteCode(inviteCode);
    } else {
      var message = new Message();
      message.setCode(Constants.ERROR_CODE_NOT_AUTHORIZED);
      message.setDescription("You aren't authorized for this!");
      response = ResponseUtil.buildFailureResponse(HttpStatus.UNAUTHORIZED, List.of(message));
    }
    return response;
  }
}
