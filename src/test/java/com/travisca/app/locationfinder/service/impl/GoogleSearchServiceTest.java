package com.travisca.app.locationfinder.service.impl;

import com.travisca.app.locationfinder.config.GoogleTestConfig;
import com.travisca.app.locationfinder.dto.google.Result;
import com.travisca.app.locationfinder.filter.impl.GoogleSearchCategoryFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@RunWith(SpringRunner.class)
@Import(GoogleTestConfig.class)
@RestClientTest(GoogleSearchService.class)
public class GoogleSearchServiceTest {
    @Autowired
    private GoogleSearchService googleSearchService;
    @Autowired
    private RestTemplate restTemplate;

    @MockBean
    private GoogleSearchCategoryFilter googleSearchCategoryFilter;

    private MockRestServiceServer server;

    @Before
    public void setUp() {
        server = MockRestServiceServer.createServer(restTemplate);
    }

    private Result stubResults(Result response) {
        List<String> types = new ArrayList<>();
        types.add("testCategory");

        Map<String, List<String>> responseAdditional = new LinkedHashMap<>();
        responseAdditional.put("results", types);

        response.setAdditionalProperty("response", responseAdditional);
        response.setPlaceId("555f7b95498e2f2bd6859b71");
        return response;
    }

    @Test

    public void testWhenServiceGetResultsSuccessfully() {
        Mockito.when(googleSearchCategoryFilter.filter(isA(Result.class), isA(Predicate.class))).thenReturn(stubResults(new Result()));
        String content = "{\"place_id\":\"555f7b95498e2f2bd6859b71\",\"response\":{\"results\":[\"testCategory\"]}}";
        this.server.expect(requestTo("https://maps.googleapis.com/maps/api/geocode/json?address=chicago&key=AIzaSyBPMSm9t2OmjzrZGOlRKiR3B10TXY8uGas"))
                .andRespond(withSuccess(content, MediaType.APPLICATION_JSON_UTF8));

        Result result = this.googleSearchService.search("chicago", "ll");
        assertThat(result.getAdditionalProperties()).isNotNull();
        assertThat(result.getPlaceId()).isEqualTo("555f7b95498e2f2bd6859b71");
        assertThat(((List<String>) ((LinkedHashMap) result.getAdditionalProperties().get("response")).get("results")).size()).isEqualTo(1);
    }

    @Test(expected = GoogleSearchServiceException.class)
    public void testWhenServiceGetResultsWithServerException() {
        this.server.expect(requestTo("https://maps.googleapis.com/maps/api/geocode/json?address=chicago&key=AIzaSyBPMSm9t2OmjzrZGOlRKiR3B10TXY8uGas"))
                .andRespond(withServerError());
        this.googleSearchService.search("chicago", "ll");
    }

    @Test(expected = GoogleSearchServiceException.class)
    public void testWhenServiceGetResultsWithBadRequest() {
        this.server.expect(requestTo("https://maps.googleapis.com/maps/api/geocode/json?address=chicago&key=AIzaSyBPMSm9t2OmjzrZGOlRKiR3B10TXY8uGas"))
                .andRespond(withBadRequest());
        this.googleSearchService.search("chicago", "ll");
    }

}
