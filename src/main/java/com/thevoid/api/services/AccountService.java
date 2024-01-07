package com.thevoid.api.services;

import com.thevoid.api.exceptions.VoidInvalidRequestException;
import com.thevoid.api.exceptions.VoidAccountNotFoundException;
import com.thevoid.api.exceptions.VoidAccountUserNameExistsException;
import com.thevoid.api.exceptions.VoidInvalidTokenException;
import com.thevoid.api.models.domain.account.Account;
import com.thevoid.api.models.contracts.user.VoidRequest;
import com.thevoid.api.models.contracts.user.VoidResponse;
import com.thevoid.api.models.domain.account.AccountDetails;
import com.thevoid.api.models.domain.account.AccountSecurity;
import com.thevoid.api.models.domain.global.Message;
import com.thevoid.api.repositories.AccountRepository;
import com.thevoid.api.services.messengers.AccountMessenger;
import com.thevoid.api.utils.ResponseUtil;
import com.thevoid.api.utils.ValidateUtil;
import com.thevoid.api.models.domain.VoidRolesEnum;
import com.thevoid.api.utils.mappers.MapStructMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class AccountService {
    private final AccountMessenger accountMessenger;
    private final AccountRepository accountRepository;
    private final MapStructMapper mapStructMapper;
    private final PermissionsService permissionsService;

    @Autowired
    public AccountService(AccountMessenger accountMessenger,
                          AccountRepository accountRepository,
                          MapStructMapper mapStructMapper,
                          PermissionsService permissionsService) {
        this.accountMessenger = accountMessenger;
        this.accountRepository = accountRepository;
        this.mapStructMapper = mapStructMapper;
        this.permissionsService = permissionsService;
    }

    public VoidResponse createAccount(String clientId, VoidRequest voidRequest) throws VoidInvalidRequestException, VoidAccountUserNameExistsException {
        log.info("Request for new Account");
        log.info("Request by ClientId: " + clientId);
        ValidateUtil.validateAccount(voidRequest);
        ValidateUtil.validateClientId(clientId);

        var account = Objects.requireNonNullElse(voidRequest.getAccount(), new Account());
        var accountDetails = Objects.requireNonNullElse(account.getAccountDetails(), new AccountDetails());
        var accountSecurity = Objects.requireNonNullElse(account.getAccountSecurity(), new AccountSecurity());
        account.setVoidRole(VoidRolesEnum.VOID_DWELLER);

        var username = ValidateUtil.cleanUsername(account);
        ValidateUtil.isValidAccountStringItem(username);

        var accountOptional = this.accountRepository.findByUsername(username);
        if (accountOptional.isEmpty()) {
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
            var response = ResponseUtil.buildSuccessfulResponse(HttpStatus.CREATED);
            response.setAccounts(List.of(account));
            var httpHeader = new HttpHeaders();
            httpHeader.add("loginToken", jwt);
            response.setHttpHeaders(httpHeader);
            return response;
        } else {
            // This means we found an account with this username already in the DB.
            throw new VoidAccountUserNameExistsException();
        }
    }

    public VoidResponse currentAccount(String clientId, String loginToken, VoidRequest voidRequest) throws VoidAccountNotFoundException, VoidInvalidRequestException {
        ValidateUtil.validateAccount(voidRequest);
        ValidateUtil.validateClientId(clientId);

        var account = Objects.requireNonNullElse(voidRequest.getAccount(), new Account());
        var username = Objects.requireNonNullElse(account.getUsername(), "");

        var accountEntity = this.accountMessenger.getAccountEntityByUsername(username);
        var accountId = accountEntity.getId();
        var accountSecurityEntity = this.accountMessenger.getAccountSecurityEntityById(accountId);
        var dbLoginToken = accountSecurityEntity.getLoginToken();
        var response = new VoidResponse();

        if (dbLoginToken.equals(loginToken)) {
            response = ResponseUtil.buildSuccessfulResponse(HttpStatus.OK);
            var myself = this.mapStructMapper.mapToAccount(accountEntity);
            response.setMyself(myself);

            // TODO: Update the JWT token in and db and in the response.

        } else {
            var message = new Message();
            message.setCode("");
            message.setDescription("LoginToken Issue");
            response = ResponseUtil.buildFailureResponse(HttpStatus.BAD_REQUEST, List.of(message));
        }

        return response;
    }

    /**
     * @param clientId
     * @param voidRequest
     * @return
     * @throws VoidInvalidRequestException
     * @throws VoidAccountNotFoundException
     */
    public VoidResponse loginAccount(String clientId, VoidRequest voidRequest) throws VoidInvalidRequestException, VoidAccountNotFoundException {
        log.info("Request to login for clientId: {}", clientId);
        ValidateUtil.validateAccount(voidRequest);
        ValidateUtil.validateClientId(clientId);
        var account = Objects.requireNonNullElse(voidRequest.getAccount(), new Account());
        var accountSecurity = Objects.requireNonNullElse(account.getAccountSecurity(), new AccountSecurity());

        // Clean up the email and password, then make sure they aren't empty
        var email = ValidateUtil.cleanEmailAddress(accountSecurity);
        var password = ValidateUtil.cleanPassword(accountSecurity);

        log.info("Validating email and password");
        ValidateUtil.isValidAccountStringItem(email);
        ValidateUtil.isValidAccountStringItem(password);

        // Validate Password
        var isAccountPasswordValid = this.permissionsService.validatePassword(email, password);
        var response = new VoidResponse();

        // Verify that the passwords are the same
        if (isAccountPasswordValid) {
            var jwt = this.permissionsService.generateJWT(email);
            response = ResponseUtil.buildSuccessfulResponse(HttpStatus.OK);
            var httpHeader = new HttpHeaders();
            httpHeader.add("loginToken", jwt);
            response.setHttpHeaders(httpHeader);
            var accountSecurityEntity = this.accountMessenger.getAccountSecurityEntityByEmail(email);
            accountSecurityEntity.setLoginToken(jwt);
            this.accountMessenger.saveAccountSecurityRepository(accountSecurityEntity);
        } else {
            //TODO: Make the message for this error
            response = ResponseUtil.buildFailureResponse(HttpStatus.BAD_REQUEST, null);
        }

        return response;
    }

    public VoidResponse logoutAccount(String clientId, String loginToken, Long accountId) throws VoidAccountNotFoundException, VoidInvalidRequestException, VoidInvalidTokenException {
        ValidateUtil.validateClientId(clientId);
        var isLoginTokenValid = this.permissionsService.validateLoginToken(loginToken, accountId);
        if (!isLoginTokenValid) {
            throw new VoidInvalidRequestException();
        }
        var accountEntity = this.accountMessenger.getAccountEntityById(accountId);
        var accountSecurityEntity = accountEntity.getAccountSecurityEntity();
        accountSecurityEntity.setLoginToken("");
        return ResponseUtil.buildSuccessfulResponse(HttpStatus.OK);
    }

    public VoidResponse getAllAccounts(String clientId) throws VoidInvalidRequestException {
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
        var response = ResponseUtil.buildSuccessfulResponse(HttpStatus.OK);
        response.setAccounts(accounts);

        return response;
    }

    public VoidResponse getAccountById(String clientId, Long accountId) throws VoidInvalidRequestException, VoidAccountNotFoundException {
        log.info("Requesting account with id:" + accountId);
        log.info("Request by ClientId: " + clientId);

        // Validate inputs
        ValidateUtil.validateClientId(clientId);

        // Pull Account info
        var accountEntity = this.accountMessenger.getAccountEntityById(accountId);
        var account = this.mapStructMapper.mapToAccount(accountEntity);
        var voidRoleEnum = VoidRolesEnum.valueOf(accountEntity.getVoidRoleEntity().getRole());
        account.setVoidRole(voidRoleEnum);

        var response = ResponseUtil.buildSuccessfulResponse(HttpStatus.OK);
        response.setAccounts(List.of(account));

        return response;
    }

    public VoidResponse getAccountByUsername(String clientId, String username) throws VoidInvalidRequestException, VoidAccountNotFoundException {
        log.info("Requesting account with id:" + username);
        log.info("Request by ClientId: " + clientId);

        ValidateUtil.validateClientId(clientId);

        var accountEntity = this.accountMessenger.getAccountEntityByUsername(username);
        var account = this.mapStructMapper.mapToAccount(accountEntity);
        var voidRoleEnum = VoidRolesEnum.valueOf(accountEntity.getVoidRoleEntity().getRole());
        account.setVoidRole(voidRoleEnum);
        var response = ResponseUtil.buildSuccessfulResponse(HttpStatus.OK);
        response.setAccounts(List.of(account));

        return response;
    }

    public VoidResponse updateRole(String clientId, Long accountId, VoidRequest voidRequest) throws VoidInvalidRequestException {
        ValidateUtil.validateAccount(voidRequest);
        ValidateUtil.validateClientId(clientId);
        log.info("Updating VoidRole for account with id:" + accountId);
        log.info("Request by ClientId: " + clientId);

        return ResponseUtil.buildSuccessfulResponse(HttpStatus.OK);
    }
}
