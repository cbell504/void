package com.thevoid.api.controllers;

import com.thevoid.api.exceptions.VoidInvalidRequestException;
import com.thevoid.api.exceptions.VoidAccountNotFoundException;
import com.thevoid.api.exceptions.VoidAccountUserNameExistsException;
import com.thevoid.api.exceptions.VoidInvalidTokenException;
import com.thevoid.api.models.contracts.user.VoidRequest;
import com.thevoid.api.models.contracts.user.VoidResponse;
import com.thevoid.api.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Get all accounts on file, clientId is required.
     *
     * @param clientId - String value that represents the client's id
     * @return all accounts
     * @throws VoidInvalidRequestException - thrown is request is considered invalid
     */
    @GetMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoidResponse> getAllAccounts(@RequestHeader String clientId) throws VoidInvalidRequestException {
        var response = this.accountService.getAllAccounts(clientId);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    /**
     * Get an account on file by a given accountId
     *
     * @param clientId  - String value that represents the client's id
     * @param accountId - Long value that represents the user's account id
     * @return an account for the given accountId
     * @throws VoidInvalidRequestException  - thrown if client id is not found
     * @throws VoidAccountNotFoundException - thrown if no accounts are on file with that id
     */
    @GetMapping(value = "/v1/accountId/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoidResponse> getAccountById(@RequestHeader String clientId,
                                                       @PathVariable Long accountId) throws VoidInvalidRequestException, VoidAccountNotFoundException {
        var response = this.accountService.getAccountById(clientId, accountId);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    /**
     * Get an account on file by a given account username
     *
     * @param clientId - String value that represents the client's id
     * @param username - String value that represents the account username
     * @return an account for the given username
     * @throws VoidInvalidRequestException  - thrown if client id is not found
     * @throws VoidAccountNotFoundException - thrown if no accounts are on file with that username
     */
    @GetMapping(value = "/v1/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoidResponse> getAccountByUsername(@RequestHeader String clientId,
                                                             @PathVariable String username) throws VoidInvalidRequestException, VoidAccountNotFoundException {
        var response = this.accountService.getAccountByUsername(clientId, username);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    /**
     * Creates an account with the given request body.
     *
     * @param clientId    - String value that represents the client's id
     * @param voidRequest - Object containing details about the account to create
     * @return an account object if the creation was successful
     * @throws VoidInvalidRequestException        - thrown if client id is not found
     * @throws VoidAccountUserNameExistsException - thrown if no accounts are on file with that username
     */
    @PostMapping(value = "/v1/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoidResponse> createAccount(@RequestHeader String clientId, @RequestBody VoidRequest voidRequest) throws VoidInvalidRequestException, VoidAccountUserNameExistsException {
        var response = this.accountService.createAccount(clientId, voidRequest);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping(value = "/v1/current", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoidResponse> current(@RequestHeader String clientId,
                                                @RequestHeader String loginToken,
                                                @RequestBody VoidRequest voidRequest) throws VoidInvalidRequestException, VoidAccountUserNameExistsException, VoidAccountNotFoundException {
        var response = this.accountService.currentAccount(clientId, loginToken, voidRequest);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    /**
     * Returns a loginToken once a user has been authenticated
     *
     * @param clientId    - String value that represents the client's id
     * @param voidRequest Object containing details about the account
     * @return a void response and a header containing the loginToken
     * @throws VoidInvalidRequestException        - thrown if client id is not found
     * @throws VoidAccountUserNameExistsException - thrown if no accounts are on file with that username
     * @throws VoidAccountNotFoundException       - thrown if no accounts are on file with that username
     */
    @PostMapping(value = "/v1/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoidResponse> loginAccount(@RequestHeader String clientId, @RequestBody VoidRequest voidRequest) throws VoidInvalidRequestException, VoidAccountNotFoundException {
        var response = this.accountService.loginAccount(clientId, voidRequest);
        return new ResponseEntity<>(response, response.getHttpHeaders(), response.getHttpStatus());
    }

    @GetMapping(value = "/v1/logout/{accountId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoidResponse> logoutAccount(@RequestHeader String clientId,
                                                      @RequestHeader String loginToken,
                                                      @PathVariable Long accountId) throws VoidInvalidRequestException, VoidAccountNotFoundException, VoidInvalidTokenException {
        var response = this.accountService.logoutAccount(clientId, loginToken, accountId);
        return new ResponseEntity<>(response, response.getHttpHeaders(), response.getHttpStatus());
    }

    @PatchMapping(value = "/v1/{accountId}/updateRole", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoidResponse> updateRole(@RequestHeader String clientId,
                                                   @RequestHeader String loginToken,
                                                   @PathVariable Long accountID,
                                                   @RequestBody VoidRequest voidRequest) throws VoidInvalidRequestException {
        var response = this.accountService.updateRole(clientId, accountID, voidRequest);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
