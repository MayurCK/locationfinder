package com.travisca.app.locationfinder.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travisca.app.locationfinder.filter.IGoogleSearchFilter;
import com.travisca.app.locationfinder.filter.impl.GoogleSearchCategoryFilter;
import com.travisca.app.locationfinder.service.ISearchService;
import com.travisca.app.locationfinder.service.impl.GoogleSearchService;
import com.travisca.app.locationfinder.settings.GoogleSearchSettings;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@Import(TestConfig.class)
@TestConfiguration
public class GoogleTestConfig {

    @Bean
    public ISearchService GoogleSearchService(RestTemplate restTemplate, GoogleSearchSettings googleSearchSettings, IGoogleSearchFilter googleSearchFilter) {
        return new GoogleSearchService(restTemplate, googleSearchSettings, googleSearchFilter);
    }

    @Bean
    @ConfigurationProperties(prefix = "google")
    public GoogleSearchSettings googleSearchSettings() {
        return new GoogleSearchSettings();
    }


}
