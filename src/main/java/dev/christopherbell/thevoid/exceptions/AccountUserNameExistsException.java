package dev.christopherbell.thevoid.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountUserNameExistsException extends Exception {

  public AccountUserNameExistsException() {
    super();
  }

  public AccountUserNameExistsException(String message, Throwable cause) {
    super(message, cause);
  }

  public AccountUserNameExistsException(String message) {
    super(message);
  }

  public AccountUserNameExistsException(Throwable cause) {
    super(cause);
  }
}
