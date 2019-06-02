package com.travisca.app.locationfinder.service.impl;

import com.travisca.app.locationfinder.dto.google.Result;
import com.travisca.app.locationfinder.filter.IGoogleSearchFilter;
import com.travisca.app.locationfinder.filter.impl.GoogleResponsePredicates;
import com.travisca.app.locationfinder.service.ISearchService;
import com.travisca.app.locationfinder.settings.GoogleSearchSettings;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;

public class GoogleSearchService implements ISearchService {
    private final RestTemplate restTemplate;
    private final GoogleSearchSettings googleSearchSettings;
    private final IGoogleSearchFilter googleSearchFilter;

    public GoogleSearchService(RestTemplate restTemplate, GoogleSearchSettings googleSearchSettings, IGoogleSearchFilter googleSearchFilter) {
        this.restTemplate = restTemplate;
        this.googleSearchSettings = googleSearchSettings;
        this.googleSearchFilter = googleSearchFilter;
    }

    @Override
    public Result search(String city, String category) {
        ResponseEntity<Result> googleSearchedResults = null;
        try {
            googleSearchedResults = this.restTemplate.exchange(buildAbsoluteUri(googleSearchSettings.getUri(), city), HttpMethod.GET, prepareHeader(), Result.class);
            handleException(googleSearchedResults);
        } catch (Exception ex) {
            throw new GoogleSearchServiceException(1001L, ex);
        }
        return getFilteredSearchList(category, googleSearchedResults.getBody());
    }

    private void handleException(ResponseEntity<Result> result) {
        if (!result.getStatusCode().is2xxSuccessful()) {
            throw new GoogleSearchServiceException(1002l);
        }
    }

    private Result getFilteredSearchList(String category, Result googleSearchedResults) {
        return googleSearchFilter.filter(googleSearchedResults, GoogleResponsePredicates.isInCategory(category));
    }

    private URI buildAbsoluteUri(String url, String city) {
        URI uri = UriComponentsBuilder.fromUriString(url)
                .buildAndExpand()
                .toUri();
        uri = UriComponentsBuilder
                .fromUri(uri)
                .queryParam("address", city)
                .queryParam("key", googleSearchSettings.getKey())
                .build()
                .encode()
                .toUri();
        return uri;
    }

    private HttpEntity<String> prepareHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.ALL));
        return new HttpEntity<>(headers);
    }

}
