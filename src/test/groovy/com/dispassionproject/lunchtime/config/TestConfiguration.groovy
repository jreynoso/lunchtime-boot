package com.dispassionproject.lunchtime.config

import com.dispassionproject.lunchtime.service.GooglePlacesLookupService
import com.google.maps.GeoApiContext
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.annotation.Scope
import spock.mock.DetachedMockFactory

@Profile("integration-test")
@Configuration
class TestConfiguration {

    def detachedMockFactory = new DetachedMockFactory()

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    GeoApiContext geoApiContext() {
        detachedMockFactory.Mock(GeoApiContext)
    }

    @Bean
    GooglePlacesLookupService googlePlacesLookupService() {
        detachedMockFactory.Mock(GooglePlacesLookupService)
    }

}
