package com.thevoid.api.controllers;

import com.thevoid.api.models.contracts.user.VoidResponse;
import com.thevoid.api.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    public SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    //TODO: This endpoint needs some security
    @GetMapping(value = "/{searchTerm}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoidResponse> search(@RequestHeader String clientId,
                                               @PathVariable String searchTerm) {
        var response = this.searchService.search(clientId, searchTerm);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
