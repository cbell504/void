package com.thevoid.api.utils;

import com.thevoid.api.configs.Constants;
import com.thevoid.api.models.contracts.global.Response;
import com.thevoid.api.models.contracts.user.VoidResponse;
import com.thevoid.api.models.contracts.global.Message;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ResponseUtil {
    private ResponseUtil() {}

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
