package com.travisca.app.locationfinder.service.impl;

import com.travisca.app.locationfinder.config.FourSquareTestConfig;
import com.travisca.app.locationfinder.config.GoogleTestConfig;
import com.travisca.app.locationfinder.dto.foursquare.Response;
import com.travisca.app.locationfinder.dto.foursquare.Venue;
import com.travisca.app.locationfinder.service.ISearchService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@Import({FourSquareTestConfig.class, GoogleTestConfig.class})
public class DataAggregatorServiceTest {

    @Autowired
    private List<ISearchService> searchServices;
    private Response stubVeneus() {
        Venue v = new Venue();
        v.setId("555f7b95498e2f2bd6859b71");
        v.setName("Chicago Helicopter Experience");

        List<Venue> venueList = new ArrayList<>();
        venueList.add(v);

        Response response = new Response();
        response.setVenues(venueList);
        return response;
    }

}
