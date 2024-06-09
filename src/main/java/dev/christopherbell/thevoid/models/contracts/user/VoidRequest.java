package dev.christopherbell.thevoid.models.contracts.user;

import dev.christopherbell.libs.common.api.contracts.Request;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.christopherbell.thevoid.models.domain.Cry;
import dev.christopherbell.thevoid.models.domain.account.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class VoidRequest extends Request {

  @JsonProperty("account")
  private Account account;
  @JsonProperty("cry")
  private Cry cry;
}
