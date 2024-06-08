package dev.christopherbell.thevoid.models.contracts.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.christopherbell.thevoid.models.domain.Cry;
import dev.christopherbell.thevoid.models.domain.account.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoidRequest {

  @JsonProperty("account")
  private Account account;
  @JsonProperty("cry")
  private Cry cry;
}
