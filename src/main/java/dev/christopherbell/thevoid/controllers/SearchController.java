package dev.christopherbell.thevoid.controllers;

import com.christopherbell.dev.libs.common.api.contracts.Response;
import dev.christopherbell.thevoid.exceptions.InvalidRequestException;
import dev.christopherbell.thevoid.models.contracts.user.VoidResponse;
import dev.christopherbell.thevoid.services.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {
  public SearchService searchService;

  //TODO: This endpoint needs some security

  /**
   *
   * @param clientId
   * @param searchTerm
   * @return
   * @throws InvalidRequestException
   */
  @GetMapping(value = "/{searchTerm}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Response<VoidResponse>> search(@RequestHeader String clientId,
      @PathVariable String searchTerm) throws InvalidRequestException {
    return new ResponseEntity<>(Response.<VoidResponse>builder()
        .payload(searchService.search(clientId, searchTerm))
        .success(true)
        .build(), HttpStatus.OK);
  }
}
