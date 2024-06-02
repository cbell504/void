package com.thevoid.api.models.contracts.user;

import com.thevoid.api.models.domain.account.Account;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountLoginResponse implements Serializable {
  private HttpHeaders httpHeaders;
  private Account myself;
}
