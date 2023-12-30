package com.thevoid.api.models.contracts.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thevoid.api.models.domain.Cry;
import com.thevoid.api.models.domain.account.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoidRequest {
    @JsonProperty("account")
    private Account account;
    @JsonProperty("cry")
    private Cry cry;
}
