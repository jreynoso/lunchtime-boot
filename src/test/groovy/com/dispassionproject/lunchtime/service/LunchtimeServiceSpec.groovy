package com.dispassionproject.lunchtime.service

import com.dispassionproject.lunchtime.BaseSpec

class LunchtimeServiceSpec extends BaseSpec {

    LunchtimeService lunchtimeService
    GooglePlacesLookupService mockGooglePlacesLookupService

    def setup() {
        mockGooglePlacesLookupService = Mock(GooglePlacesLookupService)
        lunchtimeService = new GooglePlacesLunchtimeService(mockGooglePlacesLookupService)
    }

    def "should return lunch options"() {
        given:
        def placesSearchResponse = aRandom.placesSearchResponse()
        def placeSearchResult = placesSearchResponse.results[0]
        1 * mockGooglePlacesLookupService.getPlaces(_) >> placesSearchResponse
        def loc = aRandom.geoLocation().build()

        when:
        def response = lunchtimeService.getLunchtimeOptions(loc.toUrlValue())

        then:
        response.criteria.loc == loc
        response.options.size() == 1
        response.options[0].id == placeSearchResult.placeId
        response.options[0].name == placeSearchResult.name
        response.options[0].imageUrl == placeSearchResult.icon
        response.options[0].address == placeSearchResult.formattedAddress
        response.options[0].vicinity == placeSearchResult.vicinity
        response.options[0].rating == placeSearchResult.rating
    }

}
