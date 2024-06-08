package dev.christopherbell.thevoid.controllers;

import com.christopherbell.dev.libs.common.api.contracts.Response;
import dev.christopherbell.thevoid.exceptions.AccountNotFoundException;
import dev.christopherbell.thevoid.exceptions.InvalidRequestException;
import dev.christopherbell.thevoid.exceptions.InvalidTokenException;
import dev.christopherbell.thevoid.models.contracts.user.VoidRequest;
import dev.christopherbell.thevoid.models.contracts.user.VoidResponse;
import dev.christopherbell.thevoid.services.InviteCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/invite/code")
@RequiredArgsConstructor
@RestController
public class InviteCodeController {

  public final InviteCodeService inviteCodeService;

  @PostMapping(value = "/v1", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Response<VoidResponse>> generateInviteCode(@RequestHeader String clientId,
      @RequestHeader String loginToken,
      @RequestBody VoidRequest voidRequest)
      throws InvalidRequestException, AccountNotFoundException, InvalidTokenException {
    return new ResponseEntity<>(Response.<VoidResponse>builder()
        .payload(inviteCodeService.generateInviteCode(clientId, loginToken, voidRequest))
        .success(true)
        .build(), HttpStatus.CREATED);
  }
}
