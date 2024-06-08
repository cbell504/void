package dev.christopherbell.thevoid.controllers;

import com.christopherbell.dev.libs.common.api.contracts.Message;
import com.christopherbell.dev.libs.common.api.contracts.Response;
import dev.christopherbell.thevoid.exceptions.AccountNotFoundException;
import dev.christopherbell.thevoid.exceptions.AccountUserNameExistsException;
import dev.christopherbell.thevoid.exceptions.InvalidRequestException;
import dev.christopherbell.thevoid.exceptions.InvalidTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {
  private final static String INVALID_REQUEST_CODE = "001";
  private final static String ACCOUNT_NOT_FOUND_CODE = "002";
  private final static String ACCOUNT_USER_NAME_EXISTS_CODE = "003";
  private final static String INVALID_TOKEN_CODE = "004";


  @ExceptionHandler({InvalidRequestException.class})
  public ResponseEntity<Response> handleInvalidRequestException(InvalidRequestException e) {
    log.error(e.getMessage(), e);
    return new ResponseEntity<>(
        Response.builder()
            .messages(List.of(Message.builder()
                .code(INVALID_REQUEST_CODE)
                .description(e.getMessage())
                .build()))
            .success(false)
            .build(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({AccountNotFoundException.class})
  public ResponseEntity<Response> handleAccountNotFoundException(AccountNotFoundException e) {
    log.error(e.getMessage(), e);
    return new ResponseEntity<>(
        Response.builder()
            .messages(List.of(Message.builder()
                .code(ACCOUNT_NOT_FOUND_CODE)
                .description(e.getMessage())
                .build()))
            .success(false)
            .build(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({AccountUserNameExistsException.class})
  public ResponseEntity<Response> handleAccountUserNameExists(AccountUserNameExistsException e) {
    log.error(e.getMessage(), e);
    return new ResponseEntity<>(
        Response.builder()
            .messages(List.of(Message.builder()
                .code(ACCOUNT_USER_NAME_EXISTS_CODE)
                .description(e.getMessage())
                .build()))
            .success(false)
            .build(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({InvalidTokenException.class})
  public ResponseEntity<Response> handleInvalidTokenException(InvalidTokenException e) {
    log.error(e.getMessage(), e);
    return new ResponseEntity<>(
        Response.builder()
            .messages(List.of(Message.builder()
                .code(INVALID_TOKEN_CODE)
                .description(e.getMessage())
                .build()))
            .success(false)
            .build(), HttpStatus.UNAUTHORIZED);
  }
}