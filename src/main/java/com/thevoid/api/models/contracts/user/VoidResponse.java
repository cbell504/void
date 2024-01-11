package com.thevoid.api.models.contracts.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thevoid.api.models.domain.Cry;
import com.thevoid.api.models.domain.InviteCode;
import com.thevoid.api.models.domain.account.Account;
import com.thevoid.api.models.domain.global.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpHeaders;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoidResponse extends Response {
    private List<Account> accounts;
    private List<Cry> cries;
    private HttpHeaders httpHeaders;
    private Account myself;
    private InviteCode inviteCode;
}
