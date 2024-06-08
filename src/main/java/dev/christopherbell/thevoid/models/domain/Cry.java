package dev.christopherbell.thevoid.models.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cry {

  @JsonProperty("id")
  private long id;

  @JsonProperty("creationDate")
  private String creationDate;

  @JsonProperty("expirationDate")
  private String expirationDate;

  @JsonProperty("isRootCry")
  private boolean isRootCry;

  @JsonProperty("lastAmplifiedDate")
  private String lastAmplifiedDate;

//    @JsonProperty("rootCry")
//    private Cry rootCry;

  @JsonProperty("text")
  private String text;

  @JsonProperty("author")
  private String author;

  @JsonProperty("authorAccountId")
  private String authorAccountId;

  //private AccountDto lastUserAmplified;
  //private List<CryDto> replies;

  //private long accountId;
  //@JsonProperty("account")
  //private AccountDto accountDto;
}
