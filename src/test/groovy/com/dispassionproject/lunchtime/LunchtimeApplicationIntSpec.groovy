package com.dispassionproject.lunchtime

import com.dispassionproject.lunchtime.controller.LunchtimeController
import org.springframework.beans.factory.annotation.Autowired

class LunchtimeApplicationIntSpec extends BaseIntSpec {

    @Autowired
    LunchtimeController lunchtimeController

    def "should load application context"() {
        expect:
        lunchtimeController
    }

}
