package com.travisca.app.locationfinder.filter.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travisca.app.locationfinder.dto.foursquare.Category;
import com.travisca.app.locationfinder.dto.foursquare.Response;
import com.travisca.app.locationfinder.dto.foursquare.Venue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
public class FourSquareCategoryFilterTest {

    private FourSquareCategoryFilter fourSquareCategoryFilter;

    @Before
    public void setUp() {
        fourSquareCategoryFilter = new FourSquareCategoryFilter(new ObjectMapper());
        stubVeneus(new Response());
    }

    private Response stubVeneus(Response response) {
        Category category = new Category();
        category.setName("testcategory");
        category.setId("555f7b95498e2f2bd6859b71");
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        Venue v = new Venue();
        v.setId("555f7b95498e2f2bd6859b71");
        v.setName("Chicago Helicopter Experience");
        v.setCategories(categories);

        List<Venue> venueList = new ArrayList<>();
        venueList.add(v);

        Map<String, List<Venue>> responseAdditional = new LinkedHashMap<>();
        responseAdditional.put("venues", venueList);

        response.setAdditionalProperty("response", responseAdditional);
        return response;
    }

    @Test
    public void testWhenCategoryIsPresentInVenueTest() {
        Response response = this.fourSquareCategoryFilter.filter(stubVeneus(new Response()), FourSquareResponsePredicates.isInCategory("testcategory"));
        assertThat(response.getVenues()).isNotNull();
        assertThat(response.getVenues().get(0).getId()).contains("555f7b95498e2f2bd6859b71");
        assertThat(response.getVenues().get(0).getCategories().size()).isEqualTo(1);
    }

    @Test(expected = FourSquareCategoryFilterException.class)
    public void testWhenResponseIsNullTest() {
        Response response = this.fourSquareCategoryFilter.filter(null, FourSquareResponsePredicates.isInCategory("testcategory"));
    }

}
