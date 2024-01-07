package com.thevoid.api.models.contracts.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thevoid.api.models.domain.Cry;
import com.thevoid.api.models.domain.account.Account;
import com.thevoid.api.models.domain.global.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoidResponse extends Response {
    @JsonProperty("accounts")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Account> accounts;

    @JsonProperty("cries")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Cry> cries;

    @JsonProperty("httpHeaders")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private HttpHeaders httpHeaders;

    @JsonProperty("myself")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Account myself;
}
