package dev.christopherbell.thevoid.services;

import com.christopherbell.dev.libs.common.api.exceptions.AccountNotFoundException;
import com.christopherbell.dev.libs.common.api.exceptions.InvalidTokenException;
import dev.christopherbell.thevoid.repositories.AccountRepository;
import dev.christopherbell.thevoid.repositories.AccountSecurityRepository;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class PermissionsService {

  private final AccountRepository accountRepository;
  private final AccountSecurityRepository accountSecurityRepository;

  /**
   * @param email
   * @return
   */
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

  /**
   * @param loginToken
   * @param accountId
   * @return
   * @throws AccountNotFoundException
   * @throws InvalidTokenException
   */
  public boolean validateLoginToken(String loginToken, Long accountId)
      throws AccountNotFoundException, InvalidTokenException {
    var maybeAccountEntity = this.accountRepository.findById(accountId);
    if (maybeAccountEntity.isEmpty()) {
      throw new AccountNotFoundException();
    }
    var accountEntity = maybeAccountEntity.get();
    var accountSecurityEntity = accountEntity.getAccountSecurityEntity();
    var actualLoginToken = accountSecurityEntity.getLoginToken();
    if (actualLoginToken.isBlank()) {
      throw new InvalidTokenException("Token is not valid.");
    }
    return loginToken.equals(actualLoginToken);
  }

  /**
   * @param email
   * @param password
   * @return
   * @throws AccountNotFoundException
   */
  public boolean validatePassword(String email, String password) throws AccountNotFoundException {
    var maybeAccountSecurityEntity = this.accountSecurityRepository.findByEmail(email);
    if (maybeAccountSecurityEntity.isEmpty()) {
      throw new AccountNotFoundException("No account found");
    }
    var accountSecurityEntity = maybeAccountSecurityEntity.get();
    // Get the current password from that account
    var actualPassword = accountSecurityEntity.getPassword();
    return password.equals(actualPassword);
  }
}
