package dev.christopherbell.thevoid.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoidInvalidRequestException extends Exception {

  public VoidInvalidRequestException() {
    super();
  }

  public VoidInvalidRequestException(String message, Throwable cause) {
    super(message, cause);
  }

  public VoidInvalidRequestException(String message) {
    super(message);
  }

  public VoidInvalidRequestException(Throwable cause) {
    super(cause);
  }
}
