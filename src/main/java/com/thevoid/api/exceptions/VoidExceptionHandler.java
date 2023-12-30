package com.thevoid.api.exceptions;

import com.thevoid.api.configs.Constants;
import com.thevoid.api.models.contracts.user.VoidResponse;
import com.thevoid.api.models.domain.global.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class VoidExceptionHandler {

    @ExceptionHandler({VoidInvalidRequestException.class})
    public ResponseEntity<VoidResponse> handleInvalidVoidRequestException() {
        var response = new VoidResponse();
        var message = new Message();
        message.setCode("001");
        message.setDescription("Invalid Request");
        response.setMessages(List.of(message));
        response.setHttpStatus(HttpStatus.BAD_REQUEST);
        response.setStatus(Constants.FAILURE);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @ExceptionHandler({VoidAccountNotFoundException.class})
    public ResponseEntity<VoidResponse> handleVoidAccountNotFoundException() {
        var response = new VoidResponse();
        var message = new Message();
        message.setCode("002");
        message.setDescription("No Account Found");
        response.setMessages(List.of(message));
        response.setHttpStatus(HttpStatus.NOT_FOUND);
        response.setStatus(Constants.FAILURE);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @ExceptionHandler({VoidAccountUserNameExistsException.class})
    public ResponseEntity<VoidResponse> handleVoidAccountUserNameExist() {
        var response = new VoidResponse();
        var message = new Message();
        message.setCode("003");
        message.setDescription("Username already exists!");
        response.setMessages(List.of(message));
        response.setHttpStatus(HttpStatus.BAD_REQUEST);
        response.setStatus(Constants.FAILURE);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @ExceptionHandler({VoidInvalidTokenException.class})
    public ResponseEntity<VoidResponse> handleVoidInvalidTokenException() {
        var response = new VoidResponse();
        var message = new Message();
        message.setCode("004");
        message.setDescription("Given token is not valid");
        response.setMessages(List.of(message));
        response.setHttpStatus(HttpStatus.BAD_REQUEST);
        response.setStatus(Constants.FAILURE);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
