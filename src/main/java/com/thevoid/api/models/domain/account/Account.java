package com.thevoid.api.models.domain.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thevoid.api.models.domain.Cry;
import com.thevoid.api.models.domain.VoidRolesEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    @JsonProperty("accountDetails")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AccountDetails accountDetails;

    @JsonProperty("accountSecurity")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AccountSecurity accountSecurity;

    @JsonProperty("cries")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Cry> cries;

    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @JsonProperty("username")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String username; // The user's account name

    @JsonProperty("voidRole")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private VoidRolesEnum voidRole; // This will represent what kind of account we are dealing with

    @JsonProperty("followers")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Account> followers;

    @JsonProperty("following")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Account> following;
}
