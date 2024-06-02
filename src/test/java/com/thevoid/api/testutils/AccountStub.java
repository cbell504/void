package com.thevoid.api.testutils;

import com.thevoid.api.models.domain.Address;
import com.thevoid.api.models.domain.account.Account;
import com.thevoid.api.models.domain.account.AccountDetails;
import com.thevoid.api.models.domain.account.AccountSecurity;

public class AccountStub {

  public static Account getAccount() {
    return Account.builder()
        .username("NewUser")
        .accountDetails(getAccountDetails())
        .accountSecurity(getAccountSecurity())
        .build();
  }

  public static AccountDetails getAccountDetails() {
    return AccountDetails.builder()
        .firstName("Test")
        .lastName("User")
        .phoneNumber("555-555-5555")
        .address(getAddress())
        .build();
  }

  public static AccountSecurity getAccountSecurity() {
    return AccountSecurity.builder()
        .email("test@test.com")
        .password("thebestpasswordever!")
        .build();
  }

  public static Address getAddress() {
    return Address.builder()
        .city("TestCity")
        .state("TestState")
        .country("TestCountry")
        .build();
  }

  public static String getClientId() {
    return "void-client";
  }
}
