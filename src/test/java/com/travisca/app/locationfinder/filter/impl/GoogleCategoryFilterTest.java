package com.travisca.app.locationfinder.filter.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travisca.app.locationfinder.dto.google.Result;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class GoogleCategoryFilterTest {
    private GoogleSearchCategoryFilter googleSearchCategoryFilter;

    @Before
    public void setUp() {
        googleSearchCategoryFilter = new GoogleSearchCategoryFilter(new ObjectMapper());
        stubResults(new Result());
    }

    private Result stubResults(Result response) {
        List<String> types = new ArrayList<>();
        types.add("testCategory");

        Map<String, List<String>> responseAdditional = new LinkedHashMap<>();
        responseAdditional.put("results", types);

        response.setAdditionalProperty("results", types);
        response.setPlaceId("555f7b95498e2f2bd6859b71");
        return response;
    }

    @Test
    @Ignore
    public void testWhenCategoryIsPresentInVenueTest() {
        Result result = this.googleSearchCategoryFilter.filter(stubResults(new Result()), GoogleResponsePredicates.isInCategory("testcategory"));
        assertThat(result.getAdditionalProperties()).isNotNull();
        assertThat(result.getPlaceId()).isEqualTo("555f7b95498e2f2bd6859b71");
        assertThat(((List<String>) result.getAdditionalProperties().get("results")).size()).isEqualTo(1);
    }

    @Test(expected = GoogleSearchCategoryFilterException.class)
    public void testWhenResponseIsNullTest() {
        Result result = this.googleSearchCategoryFilter.filter(null, GoogleResponsePredicates.isInCategory("testCategory"));
    }


}
