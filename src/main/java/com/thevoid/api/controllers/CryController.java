package com.thevoid.api.controllers;

import com.thevoid.api.exceptions.VoidAccountNotFoundException;
import com.thevoid.api.exceptions.VoidInvalidRequestException;
import com.thevoid.api.exceptions.VoidInvalidTokenException;
import com.thevoid.api.models.contracts.user.VoidRequest;
import com.thevoid.api.models.contracts.user.VoidResponse;
import com.thevoid.api.services.CryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cries")
public class CryController {
    private final CryService cryService;
    
    @Autowired
    public CryController(CryService cryService) {
        this.cryService = cryService;
    }

    @PostMapping(value = "/v1/create/account/{accountId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoidResponse> createCry(@RequestHeader String loginToken,
                                                  @PathVariable Long accountId,
                                                  @RequestBody VoidRequest voidRequest) throws VoidAccountNotFoundException, VoidInvalidTokenException {
        var response = this.cryService.createCry(loginToken, accountId, voidRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoidResponse> getAllCriesByAccountId(@RequestHeader String loginToken,
                                                               @PathVariable Long accountId) throws VoidAccountNotFoundException {
        var response = this.cryService.getAllCriesByAccountId(accountId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get All Cries for a Given user

    // Get All Followers for a Given User

    // Get All Following for a Given User

    // Get All replies for a given Cry

    // Get Total Lifespan for a given cry

    // Get remaining lifespan for a given cry
}
