package dev.christopherbell.thevoid.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoidAccountNotFoundException extends Exception {

  public VoidAccountNotFoundException() {
    super();
  }

  public VoidAccountNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public VoidAccountNotFoundException(String message) {
    super(message);
  }

  public VoidAccountNotFoundException(Throwable cause) {
    super(cause);
  }
}
