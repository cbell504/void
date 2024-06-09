package dev.christopherbell.thevoid.utils;

import dev.christopherbell.libs.common.api.exceptions.InvalidRequestException;
import dev.christopherbell.libs.common.api.utils.APIConstants;
import dev.christopherbell.thevoid.models.contracts.user.VoidRequest;
import dev.christopherbell.thevoid.models.domain.account.Account;
import dev.christopherbell.thevoid.models.domain.account.AccountSecurity;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ValidateUtil {

  private static final ArrayList<String> ACCEPTED_CLIENT_IDs = new ArrayList<>(
      List.of("void-client")
  );

  public static String cleanEmailAddress(AccountSecurity accountSecurity) {
    var dirtyEmail = Objects.requireNonNullElse(accountSecurity.getEmail(), APIConstants.EMPTY_STRING);
    return Jsoup.clean(dirtyEmail, Safelist.basic());
  }

  public static String cleanPassword(AccountSecurity accountSecurity) {
    var dirtyPassword = Objects.requireNonNullElse(accountSecurity.getPassword(), APIConstants.EMPTY_STRING);
    return Jsoup.clean(dirtyPassword, Safelist.basic());
  }

  public static String cleanUsername(Account account) {
    var dirtyUsername = Objects.requireNonNullElse(account.getUsername(), APIConstants.EMPTY_STRING);
    return Jsoup.clean(dirtyUsername, Safelist.basic());
  }

  private static boolean isValidClientId(String clientId) {
    return ACCEPTED_CLIENT_IDs.contains(clientId);
  }

  public static void isValidUsername(String accountString) throws InvalidRequestException {
    if (accountString.isBlank()) {
      throw new InvalidRequestException("The given username is not valid");
    }
  }

  public static void isValidEmail(String accountString) throws InvalidRequestException {
    if (accountString.isBlank()) {
      throw new InvalidRequestException("The given email is not valid");
    }
  }

  public static void isValidPassword(String accountString) throws InvalidRequestException {
    if (accountString.isBlank()) {
      throw new InvalidRequestException("The given password is not valid");
    }
  }

  public static void validateAccount(VoidRequest voidRequest) throws InvalidRequestException {
    if (Objects.isNull(voidRequest)) {
      throw new InvalidRequestException("The request is null");
    }
    if (Objects.isNull(voidRequest.getAccount())) {
      throw new InvalidRequestException("The request contains no account information");
    }
  }

  public static void validateClientId(String clientId) throws InvalidRequestException {
    if (!ValidateUtil.isValidClientId(clientId)) {
      throw new InvalidRequestException("The Client's ID is not valid.");
    }
  }
}