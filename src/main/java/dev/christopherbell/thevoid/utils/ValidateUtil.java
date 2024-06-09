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

  public static final ArrayList<String> ACCEPTED_CLIENT_IDs = new ArrayList<>(
      List.of("void-client")
  );

  public static String getCleanEmailAddress(AccountSecurity accountSecurity) {
    var dirtyEmail = Objects.requireNonNullElse(accountSecurity.getEmail(), APIConstants.EMPTY_STRING);
    return Jsoup.clean(dirtyEmail, Safelist.basic());
  }

  public static String getCleanPassword(AccountSecurity accountSecurity) {
    var dirtyPassword = Objects.requireNonNullElse(accountSecurity.getPassword(), APIConstants.EMPTY_STRING);
    return Jsoup.clean(dirtyPassword, Safelist.basic());
  }

  public static String getCleanUsername(Account account) {
    var dirtyUsername = Objects.requireNonNullElse(account.getUsername(), APIConstants.EMPTY_STRING);
    return Jsoup.clean(dirtyUsername, Safelist.basic());
  }

  public static void validateAccount(VoidRequest voidRequest) throws InvalidRequestException {
    if (Objects.isNull(voidRequest)) {
      throw new InvalidRequestException("The request is null");
    }
    if (Objects.isNull(voidRequest.getAccount())) {
      throw new InvalidRequestException("The request contains no account information");
    }
  }
}