package com.dispassionproject.lunchtime.controller

import com.dispassionproject.lunchtime.BaseIntSpec
import com.dispassionproject.lunchtime.exception.LunchtimeServiceException
import com.dispassionproject.lunchtime.service.GooglePlacesQueryService
import com.google.maps.errors.ApiException
import org.springframework.beans.factory.annotation.Autowired

class LunchtimeControllerIntSpec extends BaseIntSpec {

    @Autowired
    GooglePlacesQueryService mockGooglePlacesLookupService

    def "should get a valid response"() {
        given:
        def loc = aRandom.geoLocation().build()
        def numPlaces = aRandom.faker.number().numberBetween(1, 10)
        1 * mockGooglePlacesLookupService.getPlaces(_) >> aRandom.placesSearchResponse(numPlaces)

        when:
        def response = getLunchOptions(loc.toUrlValue())
        def lunchtimeResponse = responseToLunchtimeResponse(response)

        then:
        lunchtimeResponse.criteria.loc.latitude == loc.latitude
        lunchtimeResponse.criteria.loc.longitude == loc.longitude
        lunchtimeResponse.options.size() == numPlaces
        lunchtimeResponse.suggestion
        lunchtimeResponse.suggestion.id
        lunchtimeResponse.options.any {option -> option.id == lunchtimeResponse.suggestion.id }
    }

    def "should return valid response when there are no places matching criteria"() {
        given:
        def loc = aRandom.geoLocation().build()
        def noPlacesResponse = aRandom.placesSearchResponse()
        noPlacesResponse.results = []
        1 * mockGooglePlacesLookupService.getPlaces(_) >> noPlacesResponse

        when:
        def response = getLunchOptions(loc.toUrlValue())
        def lunchtimeResponse = responseToLunchtimeResponse(response)

        then:
        lunchtimeResponse.criteria.loc.latitude == loc.latitude
        lunchtimeResponse.criteria.loc.longitude == loc.longitude
        lunchtimeResponse.options.size() == 0
        !lunchtimeResponse.suggestion
    }

    def "should throw service exception when there's an error invoking the places api"() {
        given:
        def loc = aRandom.geoLocation().build()
        1 * mockGooglePlacesLookupService.getPlaces(_) >> { throw ApiException.from("error", "unknown") }

        when:
        getLunchOptions(loc.toUrlValue())

        then:
        def exception = thrown(Exception)
        exception.cause instanceof LunchtimeServiceException
    }

}
