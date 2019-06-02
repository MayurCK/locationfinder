package com.travisca.app.locationfinder.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travisca.app.locationfinder.filter.IFourSquareFilter;
import com.travisca.app.locationfinder.filter.impl.FourSquareCategoryFilter;
import com.travisca.app.locationfinder.service.ISearchService;
import com.travisca.app.locationfinder.service.impl.FourSquareService;
import com.travisca.app.locationfinder.settings.FourSquareSettings;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@Import(TestConfig.class)
@TestConfiguration
public class FourSquareTestConfig {
    @Bean
    public ISearchService fourSquareService(RestTemplate restTemplate, FourSquareSettings fourSquareSettings, IFourSquareFilter fourSquareFilter) {
        return new FourSquareService(restTemplate, fourSquareSettings, fourSquareFilter);
    }

    @Bean
    @ConfigurationProperties(prefix = "foursquare")
    public FourSquareSettings fourSquareSettings() {
        return new FourSquareSettings();
    }
}
