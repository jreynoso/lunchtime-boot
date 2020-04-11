package com.dispassionproject.lunchtime.testutils

import com.dispassionproject.lunchtime.api.LunchOption
import com.github.javafaker.Faker

class ARandom {

    Faker faker = new Faker()

    def lunchOption() {
        LunchOption.builder().name(faker.name().name()).build()
    }

}
