package dev.christopherbell.thevoid.controllers;

import dev.christopherbell.thevoid.exceptions.VoidAccountNotFoundException;
import dev.christopherbell.thevoid.exceptions.VoidInvalidRequestException;
import dev.christopherbell.thevoid.exceptions.VoidInvalidTokenException;
import dev.christopherbell.thevoid.models.contracts.user.VoidRequest;
import dev.christopherbell.thevoid.models.contracts.user.VoidResponse;
import dev.christopherbell.thevoid.services.InviteCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/invitecode")
public class InviteCodeController {

  public final InviteCodeService inviteCodeService;

  @Autowired
  public InviteCodeController(InviteCodeService inviteCodeService) {
    this.inviteCodeService = inviteCodeService;
  }

  @PostMapping(value = "/v1/generateInviteCode", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<VoidResponse> generateInviteCode(@RequestHeader String clientId,
      @RequestHeader String loginToken,
      @RequestBody VoidRequest voidRequest)
      throws VoidInvalidRequestException, VoidAccountNotFoundException, VoidInvalidTokenException {
    var response = this.inviteCodeService.generateInviteCode(clientId, loginToken, voidRequest);
    return new ResponseEntity<>(response, response.getHttpStatus());
  }
}
