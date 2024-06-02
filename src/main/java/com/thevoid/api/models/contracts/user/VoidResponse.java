package com.thevoid.api.models.contracts.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thevoid.api.models.domain.Cry;
import com.thevoid.api.models.domain.InviteCode;
import com.thevoid.api.models.domain.account.Account;
import com.thevoid.api.models.contracts.global.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpHeaders;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoidResponse<T> extends Response {
    private List<Account> accounts;
    private List<Cry> cries;
    private HttpHeaders httpHeaders;
    private Account myself;
    private InviteCode inviteCode;
    private AccountLoginResponse accountLoginResponse;
    private T payload;
}
