package com.thevoid.api.models.domain.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Message {
    @JsonProperty("code")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String code;
    @JsonProperty("description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;
}
