package com.dispassionproject.lunchtime.testutils

import com.dispassionproject.lunchtime.api.GeoLocation
import com.dispassionproject.lunchtime.api.LunchOption
import com.github.javafaker.Faker

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
