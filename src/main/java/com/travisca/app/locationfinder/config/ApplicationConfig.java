package com.travisca.app.locationfinder.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travisca.app.locationfinder.exception.GenericError;
import com.travisca.app.locationfinder.filter.IFourSquareFilter;
import com.travisca.app.locationfinder.filter.IGoogleSearchFilter;
import com.travisca.app.locationfinder.filter.impl.FourSquareCategoryFilter;
import com.travisca.app.locationfinder.filter.impl.GoogleSearchCategoryFilter;
import com.travisca.app.locationfinder.service.IDataAggregatorService;
import com.travisca.app.locationfinder.service.ISearchService;
import com.travisca.app.locationfinder.service.impl.DataAggregatorService;
import com.travisca.app.locationfinder.service.impl.FourSquareService;
import com.travisca.app.locationfinder.service.impl.GoogleSearchService;
import com.travisca.app.locationfinder.settings.FourSquareSettings;
import com.travisca.app.locationfinder.settings.GoogleSearchSettings;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Configuration
public class ApplicationConfig {

    @Bean
    public IDataAggregatorService dataAggregatorService(List<ISearchService> searchServices) {
        return new DataAggregatorService(searchServices);
    }

    @Bean
    public ISearchService fourSquareService(RestTemplate restTemplate, FourSquareSettings fourSquareSettings, IFourSquareFilter fourSquareFilter) {
        return new FourSquareService(restTemplate, fourSquareSettings, fourSquareFilter);
    }

    @Bean
    public ISearchService GoogleSearchService(RestTemplate restTemplate, GoogleSearchSettings googleSearchSettings, IGoogleSearchFilter googleSearchFilter) {
        return new GoogleSearchService(restTemplate, googleSearchSettings, googleSearchFilter);
    }

    @Bean
    public IGoogleSearchFilter googleSearchFilter(ObjectMapper objectMapper) {
        return new GoogleSearchCategoryFilter(objectMapper);
    }

    @Bean
    public IFourSquareFilter fourSquareCategoryFilter(ObjectMapper objectMapper) {
        return new FourSquareCategoryFilter(objectMapper);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @ConfigurationProperties(prefix = "foursquare")
    public FourSquareSettings fourSquareSettings() {
        return new FourSquareSettings();
    }


    @Bean
    @ConfigurationProperties(prefix = "google")
    public GoogleSearchSettings googleSearchSettings() {
        return new GoogleSearchSettings();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public List<GenericError> genericErrors(ObjectMapper objectMapper) throws IOException {
        return objectMapper.readValue(ResourceUtils.getFile("classpath:errors.json"), new TypeReference<List<GenericError>>() {
        });
    }
}
