package com.thevoid.api.models.domain.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response implements Serializable {
    @JsonProperty("httpStatus")
    private HttpStatus httpStatus;
    @JsonProperty("messages")
    private List<Message> messages;
    @JsonProperty("status")
    private String status;
}
