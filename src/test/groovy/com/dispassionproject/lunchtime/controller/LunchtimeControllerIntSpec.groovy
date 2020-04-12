package com.dispassionproject.lunchtime.controller

import com.dispassionproject.lunchtime.BaseIntSpec
import com.dispassionproject.lunchtime.service.GooglePlacesLookupService
import org.springframework.beans.factory.annotation.Autowired

class LunchtimeControllerIntSpec extends BaseIntSpec {

    @Autowired
    GooglePlacesLookupService mockGooglePlacesLookupService

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
    }

}
