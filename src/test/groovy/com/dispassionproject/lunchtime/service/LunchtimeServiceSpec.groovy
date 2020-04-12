package com.dispassionproject.lunchtime.service

import com.dispassionproject.lunchtime.BaseSpec

class LunchtimeServiceSpec extends BaseSpec {

    LunchtimeService lunchtimeService

    def setup() {
        lunchtimeService = new DummyLunchtimeService()
    }

    def "should return empty lunch options"() {
        given:
        def loc = aRandom.geoLocation().build()

        when:
        def response = lunchtimeService.getLunchtimeOptions(loc.toUrlValue())

        then:
        response.criteria.loc == loc
        response.options.size() == 0
    }

}
