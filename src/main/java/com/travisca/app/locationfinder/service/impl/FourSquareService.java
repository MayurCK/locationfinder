package com.travisca.app.locationfinder.service.impl;


import com.travisca.app.locationfinder.dto.foursquare.Response;
import com.travisca.app.locationfinder.filter.IFourSquareFilter;
import com.travisca.app.locationfinder.filter.impl.FourSquareResponsePredicates;
import com.travisca.app.locationfinder.service.ISearchService;
import com.travisca.app.locationfinder.settings.FourSquareSettings;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;

public class FourSquareService implements ISearchService {
    private final RestTemplate restTemplate;
    private final FourSquareSettings fourSquareSettings;
    private final IFourSquareFilter fourSquareFilter;

    public FourSquareService(RestTemplate restTemplate, FourSquareSettings fourSquareSettings, IFourSquareFilter fourSquareFilter) {
        this.restTemplate = restTemplate;
        this.fourSquareSettings = fourSquareSettings;
        this.fourSquareFilter = fourSquareFilter;
    }

    @Override
    public Response search(String city, String category) {
        ResponseEntity<Response> result = null;
        try {
            result = this.restTemplate.exchange(buildAbsoluteUri(fourSquareSettings.getUri(), city, category), HttpMethod.GET, prepareHeader(), Response.class);
            handleInvalidResponse(result);
        } catch (Exception ex) {
            throw new FourSquareServiceException(1001l, ex);
        }
        return getFilteredSearchList(category, result.getBody());
    }

    private Response getFilteredSearchList(String category, Response response) {
        return fourSquareFilter.filter(response, FourSquareResponsePredicates.isInCategory(category));
    }

    private void handleInvalidResponse(ResponseEntity<Response> result) {
        if (!result.getStatusCode().is2xxSuccessful()) {
            throw new FourSquareServiceException(1001l);
        }
    }

    private URI buildAbsoluteUri(String url, String city, String category) {
        URI uri = UriComponentsBuilder.fromUriString(url)
                .buildAndExpand()
                .toUri();
        uri = UriComponentsBuilder
                .fromUri(uri)
                .queryParam("client_id", fourSquareSettings.getClientId())
                .queryParam("client_secret", fourSquareSettings.getClientSecret())
                .queryParam("v", fourSquareSettings.getVersion())
                .queryParam("near", city)
                .queryParam("intent", "44.3,37.2")
                .build()
                .encode()
                .toUri();
        return uri;
    }

    //we can cache this as well
    private HttpEntity<String> prepareHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.ALL));
        return new HttpEntity<>(headers);
    }
}
