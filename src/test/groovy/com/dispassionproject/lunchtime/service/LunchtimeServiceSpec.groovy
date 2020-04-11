package com.dispassionproject.lunchtime.service

import com.dispassionproject.lunchtime.BaseSpec

class LunchtimeServiceSpec extends BaseSpec {

    LunchtimeService lunchtimeService

    def setup() {
        lunchtimeService = new SimpleLunchtimeService()
    }

    def "should return empty lunch options"() {
        given:
        def loc = aRandom.faker.lorem().word()

        when:
        def response = lunchtimeService.getLunchtimeOptions(loc)

        then:
        response.criteria["loc"] == loc
        response.options.size() == 0
    }

}
