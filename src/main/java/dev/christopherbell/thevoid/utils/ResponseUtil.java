package dev.christopherbell.thevoid.utils;

import dev.christopherbell.thevoid.models.contracts.user.VoidResponse;
import dev.christopherbell.thevoid.models.contracts.global.Message;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ResponseUtil {

  private ResponseUtil() {
  }

  public static VoidResponse buildFailureResponse(HttpStatus httpStatus, List<Message> messages) {
    return VoidResponse.builder()
        .messages(messages)
        .success(false)
        .httpStatus(httpStatus)
        .build();
  }

  public static VoidResponse buildSuccessfulResponse(HttpStatus httpStatus) {
    return VoidResponse.builder()
        .success(true)
        .httpStatus(httpStatus)
        .build();
  }
}
