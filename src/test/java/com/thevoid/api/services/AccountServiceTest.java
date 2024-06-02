package com.thevoid.api.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.thevoid.api.exceptions.VoidAccountUserNameExistsException;
import com.thevoid.api.exceptions.VoidInvalidRequestException;
import com.thevoid.api.models.contracts.user.VoidRequest;
import com.thevoid.api.models.db.account.AccountEntity;
import com.thevoid.api.models.domain.account.Account;
import com.thevoid.api.repositories.AccountRepository;
import com.thevoid.api.services.messengers.AccountMessenger;
import com.thevoid.api.testutils.AccountStub;
import com.thevoid.api.utils.mappers.MapStructMapper;
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
        VoidInvalidRequestException.class,
        () -> accountService.createAccount(clientId, request)
    );

    assertTrue(exception.getMessage().contains("The Client's ID is not valid."));
  }

  @Test
  public void createAccountTest_Failure_requestIsNull() {
    VoidRequest request = null;
    var clientId = AccountStub.getClientId();

    var exception = assertThrows(
        VoidInvalidRequestException.class,
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
        VoidInvalidRequestException.class,
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
        VoidInvalidRequestException.class,
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
        VoidAccountUserNameExistsException.class,
        () -> accountService.createAccount(clientId, request)
    );

    assertTrue(exception.getMessage().contains("Account with this username already exists"));
  }
}
