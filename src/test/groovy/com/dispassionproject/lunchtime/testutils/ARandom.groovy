package com.dispassionproject.lunchtime.testutils

import com.dispassionproject.lunchtime.api.GeoLocation
import com.dispassionproject.lunchtime.api.LunchOption
import com.github.javafaker.Faker
import com.google.maps.model.PlacesSearchResponse
import com.google.maps.model.PlacesSearchResult

class ARandom {

    Faker faker = new Faker()

    def lunchOption() {
        LunchOption.builder()
                .name(faker.name().name())
    }

    def geoLocation() {
        GeoLocation.builder()
                .latitude(latitude())
                .longitude(longitude())
    }

    def placesSearchResponse(def numResults = 1) {
        def response = new PlacesSearchResponse()
        def results = []
        (1..numResults).each {
            results << placesSearchResult()
        }
        response.results = results

        response
    }

    def placesSearchResult() {
        def result = new PlacesSearchResult()
        result.placeId = faker.internet().uuid()
        result.name = faker.company().name()
        result.icon = new URL(faker.internet().image())
        result.formattedAddress = faker.address().fullAddress()
        result.vicinity = faker.address().fullAddress()
        result.rating = faker.number().randomDouble(1, 0, 5).floatValue()

        result
    }

    def latitude() {
        coordinate(90)
    }

    def longitude() {
        coordinate(180)
    }

    def coordinate(range) {
        faker.number().randomDouble(8, -range, range)
    }

}
