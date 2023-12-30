package com.thevoid.api.models.domain.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

@Data
public class Response implements Serializable {
    @JsonProperty("httpStatus")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private HttpStatus httpStatus;
    @JsonProperty("messages")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Message> messages;
    @JsonProperty("status")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String status;
}
