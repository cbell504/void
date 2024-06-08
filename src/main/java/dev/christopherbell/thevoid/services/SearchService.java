package dev.christopherbell.thevoid.services;

import dev.christopherbell.thevoid.exceptions.InvalidRequestException;
import dev.christopherbell.thevoid.models.contracts.user.VoidResponse;
import dev.christopherbell.thevoid.models.domain.account.Account;
import dev.christopherbell.thevoid.services.messengers.AccountMessenger;
import dev.christopherbell.thevoid.utils.ValidateUtil;
import dev.christopherbell.thevoid.utils.mappers.MapStructMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
@Slf4j
public class SearchService {

  private final AccountMessenger accountMessenger;
  private final MapStructMapper mapStructMapper;

  public VoidResponse search(String clientId, String searchTerm) throws InvalidRequestException {
    ValidateUtil.validateClientId(clientId);
    //TODO: Make sure search term isn't malicious

    var accountEntityResults = this.accountMessenger.searchAccounts(searchTerm);
    var accountResults = new ArrayList<Account>();
    for (var accountEntity : accountEntityResults) {
      var account = this.mapStructMapper.mapToAccount(accountEntity);
      accountResults.add(account);
    }

    return VoidResponse.builder()
        .accounts(accountResults)
        .build();
  }
}
