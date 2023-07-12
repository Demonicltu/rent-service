package com.task.backend.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.backend.api.exception.error.ApplicationError;
import com.task.backend.api.exception.error.RequestError;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class BaseTestController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected ObjectMapper mapper;

    protected MockMvc getMockMvc() {
        return mockMvc;
    }

    protected void testException(ResultMatcher status, MockHttpServletRequestBuilder mockHttpServletRequestBuilder, ApplicationError applicationError)
            throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(
                        mockHttpServletRequestBuilder
                )
                .andExpect(status)
                .andReturn();

        String responseString = mvcResult.getResponse().getContentAsString();

        assertRequestError(responseString, applicationError);
    }

    protected void assertRequestError(String response, ApplicationError applicationError) throws JsonProcessingException {
        RequestError error = mapper.readValue(response, RequestError.class);

        Assertions.assertEquals(applicationError.getErrorName(), error.getErrorName());
        Assertions.assertEquals(applicationError.getHttpStatus().toString(), error.getHttpStatus());
    }

}
