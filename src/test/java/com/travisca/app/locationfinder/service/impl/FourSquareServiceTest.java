package com.travisca.app.locationfinder.service.impl;

import com.travisca.app.locationfinder.config.FourSquareTestConfig;
import com.travisca.app.locationfinder.config.TestConfig;
import com.travisca.app.locationfinder.dto.foursquare.Response;
import com.travisca.app.locationfinder.dto.foursquare.Venue;
import com.travisca.app.locationfinder.filter.impl.FourSquareCategoryFilter;
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
import java.util.List;
import java.util.function.Predicate;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@RunWith(SpringRunner.class)
@Import(FourSquareTestConfig.class)
@RestClientTest(FourSquareService.class)
public class FourSquareServiceTest {
    @Autowired
    private FourSquareService service;
    @Autowired
    private RestTemplate restTemplate;
    @MockBean
    private FourSquareCategoryFilter fourSquareCategoryFilter;


    private MockRestServiceServer server;

    @Before
    public void setUp() {
        server = MockRestServiceServer.createServer(restTemplate);
    }

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


    @Test
    public void whenServiceGetResultsSuccessfullyTest() {
        Mockito.when(fourSquareCategoryFilter.filter(isA(Response.class), isA(Predicate.class))).thenReturn(stubVeneus());
        String content = "{\"venues\":[{\"id\":\"4ba2a35bf964a520920b38e3\",\"name\":\"CTA Bus 62\",\"location\":{\"crossStreet\":\"Archer\",\"lat\":41.845975887229876,\"lng\":-87.65293804104388,\"labeledLatLngs\":[{\"label\":\"display\",\"lat\":41.845975887229876,\"lng\":-87.65293804104388}],\"postalCode\":\"60632\",\"cc\":\"US\",\"city\":\"Chicago\",\"state\":\"IL\",\"country\":\"United States\",\"formattedAddress\":[\"Archer\",\"Chicago, IL 60632\",\"United States\"]},\"categories\":[{\"id\":\"4bf58dd8d48988d12b951735\",\"name\":\"Bus Line\",\"pluralName\":\"Bus Lines\",\"shortName\":\"Bus\",\"icon\":{\"prefix\":\"https://ss3.4sqi.net/img/categories_v2/travel/busstation_\",\"suffix\":\".png\"},\"primary\":true}],\"referralId\":\"v-1559471299\",\"hasPerk\":false},{\"id\":\"583940f05da8f469b02a648a\",\"name\":\"Shubas Tavern\",\"location\":{\"lat\":41.85003,\"lng\":-87.65005,\"labeledLatLngs\":[{\"label\":\"display\",\"lat\":41.85003,\"lng\":-87.65005}],\"cc\":\"US\",\"city\":\"Chicago\",\"state\":\"IL\",\"country\":\"United States\",\"formattedAddress\":[\"Chicago, IL\",\"United States\"]},\"categories\":[{\"id\":\"4bf58dd8d48988d1e5931735\",\"name\":\"Music Venue\",\"pluralName\":\"Music Venues\",\"shortName\":\"Music Venue\",\"icon\":{\"prefix\":\"https://ss3.4sqi.net/img/categories_v2/arts_entertainment/musicvenue_\",\"suffix\":\".png\"},\"primary\":true}],\"referralId\":\"v-1559471299\",\"hasPerk\":false}],\"confident\":false,\"geocode\":{\"what\":\"\",\"where\":\"chicago\",\"feature\":{\"cc\":\"US\",\"name\":\"Chicago\",\"displayName\":\"Chicago, IL, United States\",\"matchedName\":\"Chicago, IL, United States\",\"highlightedName\":\"<b>Chicago</b>, IL, United States\",\"woeType\":7,\"slug\":\"chicago-illinois\",\"id\":\"geonameid:4887398\",\"longId\":\"72057594042815334\",\"geometry\":{\"center\":{\"lat\":41.85003,\"lng\":-87.65005},\"bounds\":{\"ne\":{\"lat\":42.023134999999996,\"lng\":-87.52366099999999},\"sw\":{\"lat\":41.644286,\"lng\":-87.940101}}}},\"parents\":[]}}";
        this.server.expect(requestTo("https://api.foursquare.com/v2/venues/search?client_id=1HRRSJUTWSLT5LHAE2D2QE05DD1B4EJB4CFMYUDWREX0TA20&client_secret=O2CSNHOYVVOUY10531545SKP0MNAZU1WXY5YLMAEVYDMUVVX&v=20190425&near=chicago&intent=44.3,37.2"))
                .andRespond(withSuccess(content, MediaType.APPLICATION_JSON_UTF8));

        Response response = this.service.search("chicago", "ll");
        assertThat(response.getVenues()).isNotNull();
        assertThat(response.getVenues().get(0).getId()).contains("555f7b95498e2f2bd6859b71");
    }

    @Test(expected = FourSquareServiceException.class)
    public void whenServiceGetResultsWithServerExceptionTest() {
        this.server.expect(requestTo("https://api.foursquare.com/v2/venues/search?client_id=1HRRSJUTWSLT5LHAE2D2QE05DD1B4EJB4CFMYUDWREX0TA20&client_secret=O2CSNHOYVVOUY10531545SKP0MNAZU1WXY5YLMAEVYDMUVVX&v=20190425&near=chicago&intent=44.3,37.2"))
                .andRespond(withServerError());
        this.service.search("chicago", "ll");
    }

    @Test(expected = FourSquareServiceException.class)
    public void whenServiceGetResultsWithBadRequeestTest() {
        this.server.expect(requestTo("https://api.foursquare.com/v2/venues/search?client_id=1HRRSJUTWSLT5LHAE2D2QE05DD1B4EJB4CFMYUDWREX0TA20&client_secret=O2CSNHOYVVOUY10531545SKP0MNAZU1WXY5YLMAEVYDMUVVX&v=20190425&near=chicago&intent=44.3,37.2"))
                .andRespond(withBadRequest());
        this.service.search("chicago", "ll");
    }


}
