package com.thevoid.api.services;

import com.thevoid.api.exceptions.VoidAccountNotFoundException;
import com.thevoid.api.exceptions.VoidInvalidRequestException;
import com.thevoid.api.exceptions.VoidInvalidTokenException;
import com.thevoid.api.repositories.AccountRepository;
import com.thevoid.api.repositories.AccountSecurityRepository;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class PermissionsService {
    private final AccountRepository accountRepository;
    private final AccountSecurityRepository accountSecurityRepository;

    @Autowired
    public PermissionsService(AccountRepository accountRepository,
                              AccountSecurityRepository accountSecurityRepository) {
        this.accountRepository = accountRepository;
        this.accountSecurityRepository = accountSecurityRepository;
    }

    public String generateJWT(String email) {
        return Jwts.builder()
                //.claim("accountId", accountId)
                .claim("email", email)
                .setSubject("login")
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(5l, ChronoUnit.MINUTES)))
                .compact();
    }

    public boolean validateLoginToken(String loginToken, Long accountId) throws VoidAccountNotFoundException, VoidInvalidTokenException {
        var maybeAccountEntity = this.accountRepository.findById(accountId);
        if (maybeAccountEntity.isEmpty()) {
            throw new VoidAccountNotFoundException();
        }
        var accountEntity = maybeAccountEntity.get();
        var accountSecurityEntity = accountEntity.getAccountSecurityEntity();
        var actualLoginToken = accountSecurityEntity.getLoginToken();
        if(actualLoginToken.isBlank()) {
            throw new VoidInvalidTokenException();
        }
        return loginToken.equals(actualLoginToken);
    }

    public boolean validatePassword(String email, String password) throws VoidAccountNotFoundException {
        var maybeAccountSecurityEntity = this.accountSecurityRepository.findByEmail(email);
        if(maybeAccountSecurityEntity.isEmpty()) {
            log.error("Account not found!");
            throw new VoidAccountNotFoundException();
        }
        var accountSecurityEntity = maybeAccountSecurityEntity.get();
        // Get the current password from that account
        var actualPassword = accountSecurityEntity.getPassword();
        return password.equals(actualPassword);
    }
}
