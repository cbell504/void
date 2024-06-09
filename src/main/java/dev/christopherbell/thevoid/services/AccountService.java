package dev.christopherbell.thevoid.services;

import com.christopherbell.dev.libs.common.api.exceptions.AccountNotFoundException;
import com.christopherbell.dev.libs.common.api.exceptions.InvalidRequestException;
import com.christopherbell.dev.libs.common.api.exceptions.InvalidTokenException;
import com.christopherbell.dev.libs.common.api.exceptions.ResourceExistsException;
import dev.christopherbell.thevoid.models.domain.account.Account;
import dev.christopherbell.thevoid.models.contracts.user.VoidRequest;
import dev.christopherbell.thevoid.models.contracts.user.VoidResponse;
import dev.christopherbell.thevoid.models.domain.account.AccountDetails;
import dev.christopherbell.thevoid.models.domain.account.AccountSecurity;
import dev.christopherbell.thevoid.repositories.AccountRepository;
import dev.christopherbell.thevoid.services.messengers.AccountMessenger;
import dev.christopherbell.thevoid.utils.ValidateUtil;
import dev.christopherbell.thevoid.models.domain.VoidRolesEnum;
import dev.christopherbell.thevoid.utils.mappers.MapStructMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Slf4j
public class AccountService {

  private final AccountMessenger accountMessenger;
  private final AccountRepository accountRepository;
  private final MapStructMapper mapStructMapper;
  private final PermissionsService permissionsService;

  /**
   *
   * @param clientId
   * @param voidRequest
   * @return
   * @throws InvalidRequestException
   * @throws ResourceExistsException
   */
  public VoidResponse createAccount(String clientId, VoidRequest voidRequest)
      throws InvalidRequestException, ResourceExistsException {
    log.info("Request for new Account");
    log.info("Request by ClientId: " + clientId);
    ValidateUtil.validateAccount(voidRequest);
    ValidateUtil.validateClientId(clientId);

    var account = Objects.requireNonNullElse(voidRequest.getAccount(), new Account());
    var accountDetails = Objects.requireNonNullElse(account.getAccountDetails(), new AccountDetails());
    var accountSecurity = Objects.requireNonNullElse(account.getAccountSecurity(), new AccountSecurity());
    account.setVoidRole(VoidRolesEnum.VOID_DWELLER);

    var username = ValidateUtil.cleanUsername(account);
    ValidateUtil.isValidUsername(username);

    var accountOptional = this.accountRepository.findByUsername(username);
    if (accountOptional.isPresent()) {
      // This means we found an account with this username already in the DB.
      throw new ResourceExistsException("Account with this username already exists");
    } else {
      var voidRoleEntity = this.mapStructMapper.mapToVoidRoleEntity(VoidRolesEnum.VOID_DWELLER);
      var accountEntity = this.mapStructMapper.mapToAccountEntity(account);
      accountEntity.setVoidRoleEntity(voidRoleEntity);
      var accountDetailsEntity = this.mapStructMapper.mapToAccountDetailsEntity(accountDetails);
      var timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
      accountDetailsEntity.setCreationDate(timeStamp);
      accountDetailsEntity.setLastLoginDate(timeStamp);
      accountDetailsEntity.setAccountEntity(accountEntity);
      var accountSecurityEntity = this.mapStructMapper.mapToAccountSecurityEntity(accountSecurity);
      accountSecurityEntity.setAccountEntity(accountEntity);

      //TODO: Clean this email before we do anything with it.
      var email = accountSecurity.getEmail();
      var jwt = this.permissionsService.generateJWT(email);

      accountSecurityEntity.setLoginToken(jwt);

      // Save all entities to the DB
      this.accountMessenger.saveAccountRepository(accountEntity);
      this.accountMessenger.saveAccountDetailsRepository(accountDetailsEntity);
      this.accountMessenger.saveAccountSecurityRepository(accountSecurityEntity);

      account = this.mapStructMapper.mapToAccount(accountEntity);
      var httpHeader = new HttpHeaders();
      httpHeader.add("loginToken", jwt);

      return VoidResponse.builder()
          .accounts(List.of(account))
          .httpHeaders(httpHeader)
          .build();
    }
  }

  /**
   * @param clientId    - id used to describe the client caller
   * @param loginToken  - token a user receives after logging into their account
   * @param voidRequest - standard request body for a Void API request
   * @return response with details of the current user.
   * @throws AccountNotFoundException - if we can't match the username with one in the db
   * @throws InvalidRequestException  - if the requestBody is null
   */
  public VoidResponse getActiveAccount(String clientId, String loginToken, VoidRequest voidRequest)
      throws AccountNotFoundException, InvalidRequestException, InvalidTokenException {
    ValidateUtil.validateAccount(voidRequest);
    ValidateUtil.validateClientId(clientId);

    var account = Objects.requireNonNullElse(voidRequest.getAccount(), new Account());
    var username = Objects.requireNonNullElse(account.getUsername(), "");

    var accountEntity = this.accountMessenger.getAccountEntityByUsername(username);
    var accountSecurityEntity = accountEntity.getAccountSecurityEntity();
    var dbLoginToken = accountSecurityEntity.getLoginToken();

    if (!dbLoginToken.equals(loginToken)) {
      throw new InvalidTokenException("Login token is not valid");
    }

    return VoidResponse.builder()
        .myself(this.mapStructMapper.mapToAccount(accountEntity))
        .build();
  }

  /**
   * @param clientId    - id used to describe the client caller
   * @param voidRequest - standard request body for a Void API request
   * @return
   * @throws InvalidRequestException
   * @throws AccountNotFoundException
   */
  public VoidResponse loginAccount(String clientId, VoidRequest voidRequest)
      throws InvalidRequestException, AccountNotFoundException, InvalidTokenException {
    log.info("Request to login for clientId: {}", clientId);
    ValidateUtil.validateAccount(voidRequest);
    ValidateUtil.validateClientId(clientId);
    var account = Objects.requireNonNullElse(voidRequest.getAccount(), new Account());
    var accountSecurity = Objects.requireNonNullElse(account.getAccountSecurity(), new AccountSecurity());

    // Clean up the email and password, then make sure they aren't empty
    var email = ValidateUtil.cleanEmailAddress(accountSecurity);
    var password = ValidateUtil.cleanPassword(accountSecurity);

    log.info("Validating email and password");
    ValidateUtil.isValidEmail(email);
    ValidateUtil.isValidPassword(password);

    // Validate Password
    var isAccountPasswordValid = this.permissionsService.validatePassword(email, password);
    var response = new VoidResponse();

    // Verify that the passwords are the same
    if (!isAccountPasswordValid) {
      throw new InvalidTokenException("Token not valid");
    } else {
      // Generate and save the JWT to the DB
      var jwt = this.permissionsService.generateJWT(email);
      var accountSecurityEntity = this.accountMessenger.getAccountSecurityEntityByEmail(email);
      accountSecurityEntity.setLoginToken(jwt);
      this.accountMessenger.saveAccountSecurityRepository(accountSecurityEntity);
      // Pull our current account from accountSecurityEntity. We want to do this because when we first login,
      // the frontend won't know much about this account. We want to give them the basic items need to make more
      // request to the backend.
      var accountEntity = accountSecurityEntity.getAccountEntity();
      var httpHeader = new HttpHeaders();
      httpHeader.add("loginToken", jwt);

      return VoidResponse.builder()
          .httpHeaders(httpHeader)
          .myself(this.mapStructMapper.mapToAccount(accountEntity))
          .build();
    }
  }

  /**
   *
   * @param clientId
   * @param loginToken
   * @param accountId
   * @return
   * @throws AccountNotFoundException
   * @throws InvalidRequestException
   * @throws InvalidTokenException
   */
  public VoidResponse logoutAccount(String clientId, String loginToken, Long accountId)
      throws AccountNotFoundException, InvalidRequestException, InvalidTokenException {
    ValidateUtil.validateClientId(clientId);
    var isLoginTokenValid = this.permissionsService.validateLoginToken(loginToken, accountId);
    if (!isLoginTokenValid) {
      throw new InvalidTokenException("Token is not valid.");
    }

    var accountEntity = this.accountMessenger.getAccountEntityById(accountId);
    var accountSecurityEntity = accountEntity.getAccountSecurityEntity();
    accountSecurityEntity.setLoginToken("");

    return VoidResponse.builder().build();
  }

  /**
   *
   * @param clientId
   * @return
   * @throws InvalidRequestException
   */
  public VoidResponse getAllAccounts(String clientId) throws InvalidRequestException {
    log.info("Request for all Accounts");
    log.info("Request by ClientId: " + clientId);

    ValidateUtil.validateClientId(clientId);

    var accountEntities = this.accountMessenger.getAccountEntities();
    var accounts = new ArrayList<Account>();
    for (var accountEntity : accountEntities) {
      var account = this.mapStructMapper.mapToAccount(accountEntity);
      var voidRoleEnum = VoidRolesEnum.valueOf(accountEntity.getVoidRoleEntity().getRole());
      account.setVoidRole(voidRoleEnum);
      accounts.add(account);
    }

    return VoidResponse.builder()
        .accounts(accounts)
        .build();
  }

  /**
   *
   * @param clientId
   * @param accountId
   * @return
   * @throws InvalidRequestException
   * @throws AccountNotFoundException
   */
  public VoidResponse getAccountById(String clientId, Long accountId)
      throws InvalidRequestException, AccountNotFoundException {
    log.info("Requesting account with id:" + accountId);
    log.info("Request by ClientId: " + clientId);

    // Validate inputs
    ValidateUtil.validateClientId(clientId);

    // Pull Account info
    var accountEntity = this.accountMessenger.getAccountEntityById(accountId);
    var account = this.mapStructMapper.mapToAccount(accountEntity);
    var voidRoleEnum = VoidRolesEnum.valueOf(accountEntity.getVoidRoleEntity().getRole());
    account.setVoidRole(voidRoleEnum);

    return VoidResponse.builder()
        .accounts(List.of(account))
        .build();
  }

  /**
   *
   * @param clientId
   * @param username
   * @return
   * @throws InvalidRequestException
   * @throws AccountNotFoundException
   */
  public VoidResponse getAccountByUsername(String clientId, String username)
      throws InvalidRequestException, AccountNotFoundException {
    log.info("Requesting account with id:" + username);
    log.info("Request by ClientId: " + clientId);

    ValidateUtil.validateClientId(clientId);

    var accountEntity = this.accountMessenger.getAccountEntityByUsername(username);
    var account = this.mapStructMapper.mapToAccount(accountEntity);
    var voidRoleEnum = VoidRolesEnum.valueOf(accountEntity.getVoidRoleEntity().getRole());
    account.setVoidRole(voidRoleEnum);

    return VoidResponse.builder()
        .accounts(List.of(account))
        .build();
  }

  /**
   *
   * @param clientId
   * @param accountId
   * @param voidRequest
   * @return
   * @throws InvalidRequestException
   */
  public VoidResponse updateRole(String clientId, Long accountId, VoidRequest voidRequest)
      throws InvalidRequestException {
    ValidateUtil.validateAccount(voidRequest);
    ValidateUtil.validateClientId(clientId);
    log.info("Updating VoidRole for account with id:" + accountId);
    log.info("Request by ClientId: " + clientId);

    return VoidResponse.builder().build();
  }
}
