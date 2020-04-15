package com.dispassionproject.lunchtime.controller

import com.dispassionproject.lunchtime.BaseIntSpec
import com.dispassionproject.lunchtime.exception.LunchtimeServiceException
import com.dispassionproject.lunchtime.service.GooglePlacesQueryService
import com.google.maps.errors.ApiException
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Unroll

import static com.dispassionproject.lunchtime.api.Accessibility.DRIVE
import static com.dispassionproject.lunchtime.api.Accessibility.SCOOT
import static com.dispassionproject.lunchtime.api.Accessibility.WALK

class LunchtimeControllerIntSpec extends BaseIntSpec {

    @Autowired
    GooglePlacesQueryService mockGooglePlacesLookupService

    def "should get a valid response"() {
        given:
        def loc = aRandom.geoLocation().build()
        def numPlaces = aRandom.faker.number().numberBetween(1, 10)
        1 * mockGooglePlacesLookupService.getPlaces(_, DRIVE.radius) >> aRandom.placesSearchResponse(numPlaces)

        when:
        def response = getLunchOptions("loc=${loc.toUrlValue()}")
        def lunchtimeResponse = responseToLunchtimeResponse(response)

        then:
        lunchtimeResponse.criteria.loc.latitude == loc.latitude
        lunchtimeResponse.criteria.loc.longitude == loc.longitude
        lunchtimeResponse.criteria.mode == "${DRIVE}"
        lunchtimeResponse.options.size() == numPlaces
        lunchtimeResponse.suggestion
        lunchtimeResponse.suggestion.id
        lunchtimeResponse.options.any { option -> option.id == lunchtimeResponse.suggestion.id }
    }

    def "should return valid response when there are no places matching criteria"() {
        given:
        def loc = aRandom.geoLocation().build()
        def noPlacesResponse = aRandom.placesSearchResponse()
        noPlacesResponse.results = []
        1 * mockGooglePlacesLookupService.getPlaces(_, DRIVE.radius) >> noPlacesResponse

        when:
        def response = getLunchOptions("loc=${loc.toUrlValue()}")
        def lunchtimeResponse = responseToLunchtimeResponse(response)

        then:
        lunchtimeResponse.criteria.loc.latitude == loc.latitude
        lunchtimeResponse.criteria.loc.longitude == loc.longitude
        lunchtimeResponse.criteria.mode == "${DRIVE}"
        lunchtimeResponse.options.size() == 0
        !lunchtimeResponse.suggestion
    }

    def "should throw service exception when there's an error invoking the places api"() {
        given:
        def loc = aRandom.geoLocation().build()
        1 * mockGooglePlacesLookupService.getPlaces(_, DRIVE.radius) >> { throw ApiException.from("error", "unknown") }

        when:
        getLunchOptions("loc=${loc.toUrlValue()}")

        then:
        def exception = thrown(Exception)
        exception.cause instanceof LunchtimeServiceException
    }

    @Unroll
    def "should return expected response when mode is #mode"() {
        given:
        def loc = aRandom.geoLocation().build()
        1 * mockGooglePlacesLookupService.getPlaces(_, mode.radius) >> aRandom.placesSearchResponse(numPlaces)

        when:
        def response = getLunchOptions("loc=${loc.toUrlValue()}&mode=${mode}")
        def lunchtimeResponse = responseToLunchtimeResponse(response)

        then:
        lunchtimeResponse.criteria.loc.latitude == loc.latitude
        lunchtimeResponse.criteria.loc.longitude == loc.longitude
        lunchtimeResponse.criteria.mode == "${mode}"
        lunchtimeResponse.options.size() == numPlaces

        where:
        mode  || numPlaces
        WALK  || 2
        SCOOT || 4
        DRIVE || 16
    }

}
