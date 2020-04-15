package com.dispassionproject.lunchtime

import com.dispassionproject.lunchtime.api.LunchtimeResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultMatcher

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("integration-test")
class BaseIntSpec extends BaseSpec {

    @Autowired
    ObjectMapper objectMapper
    @Autowired
    MockMvc mvc

    def getLunchOptions(String queryParams, ResultMatcher expectedStatus = status().isOk()) {
        MvcResult result = mvc.perform(get("/lunchtime?${queryParams}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(expectedStatus)
                .andReturn()

        result.getResponse()
    }

    def responseToLunchtimeResponse(MockHttpServletResponse response) {
        objectMapper.readValue(response.getContentAsByteArray(), LunchtimeResponse)
    }

}
