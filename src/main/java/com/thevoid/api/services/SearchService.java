package com.thevoid.api.services;

import com.thevoid.api.exceptions.VoidInvalidRequestException;
import com.thevoid.api.models.contracts.user.VoidResponse;
import com.thevoid.api.models.domain.account.Account;
import com.thevoid.api.services.messengers.AccountMessenger;
import com.thevoid.api.utils.ValidateUtil;
import com.thevoid.api.utils.mappers.MapStructMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SearchService {
    private final AccountMessenger accountMessenger;
    private final MapStructMapper mapStructMapper;

    @Autowired
    public SearchService(AccountMessenger accountMessenger,
                         MapStructMapper mapStructMapper) {
        this.accountMessenger = accountMessenger;
        this.mapStructMapper = mapStructMapper;
    }

    public VoidResponse search(String clientId, String searchTerm) throws VoidInvalidRequestException {
        ValidateUtil.validateClientId(clientId);
        //TODO: Make sure search term isn't malicious

        var accountEntityResults = this.accountMessenger.searchAccounts(searchTerm);
        var accountResults = new ArrayList<Account>();
        for(var accountEntity: accountEntityResults) {
            var account = this.mapStructMapper.mapToAccount(accountEntity);
            accountResults.add(account);
        }
        var response = new VoidResponse();
        response.setAccounts(accountResults);
        response.setHttpStatus(HttpStatus.OK);

        return response;
    }
}
