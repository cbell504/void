package com.thevoid.api.models.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VoidRole {
    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @JsonProperty("role")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String role;
}
