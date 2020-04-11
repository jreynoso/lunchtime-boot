package com.dispassionproject.lunchtime.controller

import com.dispassionproject.lunchtime.BaseIntSpec

class LunchtimeControllerIntSpec extends BaseIntSpec {

    def "should get a response"() {
        given:
        def loc = aRandom.faker.lorem().word()

        when:
        def response = getLunchOptions(loc)
        def lunchtimeResponse = responseToLunchtimeResponse(response)

        then:
        lunchtimeResponse.criteria["loc"] == loc
        lunchtimeResponse.options.size() == 0
    }

}
