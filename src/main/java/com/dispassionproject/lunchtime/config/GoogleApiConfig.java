package com.dispassionproject.lunchtime.config;

import com.google.maps.GeoApiContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

@Profile("!integration-test")
@Configuration
public class GoogleApiConfig {

    @Value("${google.api.key}")
    private String googleApiKey;

    @Value("${google.api.rateLimit:10}")
    private Integer googleApiRateLimit;

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public GeoApiContext geoApiContext() {
        return new GeoApiContext.Builder()
                .apiKey(googleApiKey)
                .queryRateLimit(googleApiRateLimit)
                .build();
    }

}
