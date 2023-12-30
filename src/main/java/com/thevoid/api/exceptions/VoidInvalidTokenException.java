package com.thevoid.api.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoidInvalidTokenException extends Exception{
    public VoidInvalidTokenException() {
        super();
    }
    public VoidInvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }
    public VoidInvalidTokenException(String message) {
        super(message);
    }
    public VoidInvalidTokenException(Throwable cause) {
        super(cause);
    }
}
