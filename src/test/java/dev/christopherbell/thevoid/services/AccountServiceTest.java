package dev.christopherbell.thevoid.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import dev.christopherbell.libs.common.api.exceptions.InvalidRequestException;
import dev.christopherbell.libs.common.api.exceptions.ResourceExistsException;
import dev.christopherbell.libs.common.api.utils.APIConstants;
import dev.christopherbell.thevoid.models.contracts.user.VoidRequest;
import dev.christopherbell.thevoid.models.db.account.AccountEntity;
import dev.christopherbell.thevoid.models.domain.account.Account;
import dev.christopherbell.thevoid.repositories.AccountRepository;
import dev.christopherbell.thevoid.services.messengers.AccountMessenger;
import dev.christopherbell.thevoid.testutils.AccountStub;
import dev.christopherbell.thevoid.utils.mappers.MapStructMapper;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
  @InjectMocks
  private AccountService accountService;
  @Mock
  private AccountMessenger accountMessenger;
  @Mock
  private AccountRepository accountRepository;
  @Mock
  private MapStructMapper mapStructMapper;
  @Mock
  private PermissionsService permissionsService;

  @Test
  public void createAccountTest_Failure_badClientId() {
   var request = VoidRequest.builder()
       .account(AccountStub.getAccount())
       .build();
   var clientId = "void-api";

    var exception = assertThrows(
        InvalidRequestException.class,
        () -> accountService.createAccount(clientId, request)
    );

    assertTrue(exception.getMessage().contains(APIConstants.VALIDATION_BAD_CLIENT_ID));
  }

  @Test
  public void createAccountTest_Failure_requestIsNull() {
    VoidRequest request = null;
    var clientId = AccountStub.getClientId();

    var exception = assertThrows(
        InvalidRequestException.class,
        () -> accountService.createAccount(clientId, request)
    );

    assertTrue(exception.getMessage().contains("The request is null"));
  }

  @Test
  public void createAccountTest_Failure_requestHasNoAccountInfo() {
    VoidRequest request = VoidRequest.builder()
        .account(null)
        .build();
    var clientId = AccountStub.getClientId();

    var exception = assertThrows(
        InvalidRequestException.class,
        () -> accountService.createAccount(clientId, request)
    );

    assertTrue(exception.getMessage().contains("The request contains no account information"));
  }

  @Test
  public void createAccountTest_Failure_requestHasBlankUserName() {
    VoidRequest request = VoidRequest.builder()
        .account(Account.builder()
            .username("")
            .build())
        .build();
    var clientId = AccountStub.getClientId();

    var exception = assertThrows(
        InvalidRequestException.class,
        () -> accountService.createAccount(clientId, request)
    );

    assertTrue(exception.getMessage().contains("The given username is not valid"));
  }

  @Test
  public void createAccountTest_Failure_accountWithUserExists() {
    VoidRequest request = VoidRequest.builder()
        .account(AccountStub.getAccount())
        .build();
    var clientId = AccountStub.getClientId();

    when(accountRepository.findByUsername(anyString())).thenReturn(Optional.of(new AccountEntity()));

    var exception = assertThrows(
        ResourceExistsException.class,
        () -> accountService.createAccount(clientId, request)
    );

    assertTrue(exception.getMessage().contains("Account with this username already exists"));
  }
}
