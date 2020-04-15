package com.dispassionproject.lunchtime.service

import com.dispassionproject.lunchtime.BaseSpec
import com.dispassionproject.lunchtime.api.Accessibility
import spock.lang.Unroll

class LunchtimeServiceSpec extends BaseSpec {

    LunchtimeService lunchtimeService
    GooglePlacesQueryService mockGooglePlacesLookupService

    def setup() {
        mockGooglePlacesLookupService = Mock(GooglePlacesQueryService)
        lunchtimeService = new DefaultLunchtimeService(mockGooglePlacesLookupService)
    }

    @Unroll
    def "should return lunch options where accessibility is #mode"() {
        given:
        def placesSearchResponse = aRandom.placesSearchResponse()
        def placeSearchResult = placesSearchResponse.results[0]
        1 * mockGooglePlacesLookupService.getPlaces(_, ((Accessibility) mode).radius) >> placesSearchResponse
        def loc = aRandom.geoLocation().build()

        when:
        def response = lunchtimeService.getLunchtimeOptions(loc.toUrlValue(), (Accessibility) mode)

        then:
        response.criteria.loc == loc
        response.criteria.mode == mode
        response.options.size() == 1
        response.options[0].id == placeSearchResult.placeId
        response.options[0].name == placeSearchResult.name
        response.options[0].imageUrl == placeSearchResult.icon
        response.options[0].address == placeSearchResult.vicinity
        response.options[0].rating == placeSearchResult.rating
        response.suggestion.id == placeSearchResult.placeId
        response.suggestion.name == placeSearchResult.name
        response.suggestion.imageUrl == placeSearchResult.icon
        response.suggestion.address == placeSearchResult.vicinity
        response.suggestion.rating == placeSearchResult.rating

        where:
        mode << Accessibility.values()
    }

}
