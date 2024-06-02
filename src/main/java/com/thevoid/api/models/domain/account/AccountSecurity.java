package com.thevoid.api.models.domain.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class AccountSecurity {
    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @JsonProperty("email")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;

    @JsonProperty("loginToken")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String loginToken;

    @JsonProperty("password")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;
}
