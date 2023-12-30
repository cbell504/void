package com.thevoid.api.utils;

import com.thevoid.api.configs.Constants;
import com.thevoid.api.models.contracts.user.VoidResponse;
import com.thevoid.api.models.domain.global.Message;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ResponseUtil {
    private ResponseUtil() {}

    public static VoidResponse buildFailureResponse(HttpStatus httpStatus, List<Message> messages) {
        var response = new VoidResponse();
        response.setHttpStatus(httpStatus);
        response.setMessages(messages);
        response.setStatus(Constants.FAILURE);
        return response;
    }

    public static VoidResponse buildSuccessfulResponse(HttpStatus httpStatus) {
        var response = new VoidResponse();
        response.setHttpStatus(httpStatus);
        response.setStatus(Constants.SUCCESS);
        return response;
    }
}
