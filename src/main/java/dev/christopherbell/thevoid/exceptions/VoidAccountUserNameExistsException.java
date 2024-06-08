package dev.christopherbell.thevoid.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoidAccountUserNameExistsException extends Exception {

  public VoidAccountUserNameExistsException() {
    super();
  }

  public VoidAccountUserNameExistsException(String message, Throwable cause) {
    super(message, cause);
  }

  public VoidAccountUserNameExistsException(String message) {
    super(message);
  }

  public VoidAccountUserNameExistsException(Throwable cause) {
    super(cause);
  }
}
