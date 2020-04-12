package com.dispassionproject.lunchtime.controller

import com.dispassionproject.lunchtime.BaseIntSpec

class LunchtimeControllerIntSpec extends BaseIntSpec {

    def "should get a response"() {
        given:
        def loc = aRandom.geoLocation().build()

        when:
        def response = getLunchOptions(loc.toUrlValue())
        def lunchtimeResponse = responseToLunchtimeResponse(response)

        then:
        lunchtimeResponse.criteria.loc.latitude == loc.latitude
        lunchtimeResponse.criteria.loc.longitude == loc.longitude
        lunchtimeResponse.options.size() == 0
    }

}
