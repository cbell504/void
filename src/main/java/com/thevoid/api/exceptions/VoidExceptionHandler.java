package com.thevoid.api.exceptions;

import com.thevoid.api.models.contracts.global.Response;
import com.thevoid.api.models.contracts.user.VoidResponse;
import com.thevoid.api.models.contracts.global.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class VoidExceptionHandler {

    @ExceptionHandler({VoidInvalidRequestException.class})
    public ResponseEntity<Response> handleInvalidVoidRequestException() {
        return new ResponseEntity<>(
            VoidResponse.builder()
                .messages(List.of(Message.builder()
                    .code("001")
                    .description("Invalid Request")
                    .build()))
                .success(false)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({VoidAccountNotFoundException.class})
    public ResponseEntity<Response> handleVoidAccountNotFoundException() {
        return new ResponseEntity<>(
            VoidResponse.builder()
                .messages(List.of(Message.builder()
                    .code("002")
                    .description("No Account Found")
                    .build()))
                .success(false)
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({VoidAccountUserNameExistsException.class})
    public ResponseEntity<Response> handleVoidAccountUserNameExist() {
        return new ResponseEntity<>(
            VoidResponse.builder()
                .messages(List.of(Message.builder()
                    .code("003")
                    .description("Username already exists!")
                    .build()))
                .success(false)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({VoidInvalidTokenException.class})
    public ResponseEntity<Response> handleVoidInvalidTokenException() {
        return new ResponseEntity<>(
            VoidResponse.builder()
                .messages(List.of(Message.builder()
                    .code("004")
                    .description("Username already exists!")
                    .build()))
                .success(false)
                .build(), HttpStatus.UNAUTHORIZED);
    }
}
