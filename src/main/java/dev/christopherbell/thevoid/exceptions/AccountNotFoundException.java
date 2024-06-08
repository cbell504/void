package dev.christopherbell.thevoid.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountNotFoundException extends Exception {

  public AccountNotFoundException() {
    super();
  }

  public AccountNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public AccountNotFoundException(String message) {
    super(message);
  }

  public AccountNotFoundException(Throwable cause) {
    super(cause);
  }
}
