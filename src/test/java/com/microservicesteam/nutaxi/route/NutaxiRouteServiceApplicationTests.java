package com.microservicesteam.nutaxi.route;

import static com.google.common.base.Throwables.propagate;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.microservicesteam.nutaxi.route.model.RouteDetails;
import com.microservicesteam.nutaxi.route.service.RouteService;

@RunWith(SpringRunner.class)
@WebMvcTest
@ActiveProfiles("test")
public class NutaxiRouteServiceApplicationTests {

    private static final String TEST_RESPONSE = getSampleContent("response.json");

    private static final String TEST_REQUEST = getSampleContent("request.json");

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    @MockBean
    private RouteService mockRouteService;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void aTest() throws Exception {
        when(mockRouteService.getRoute(anyString(), anyString(), anyString())).thenReturn((RouteDetails) new TestRouteDetails());
        mockMvc.perform(get("/api/route")
                .accept(APPLICATION_JSON)
                .param("origin", "Budapest Futó u. 47")
                .param("destination", "Budapest Corvin-negyed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.request", equalTo(TEST_REQUEST)))
                .andExpect(jsonPath("$.response", equalTo(TEST_RESPONSE)))
                .andExpect(jsonPath("$.error", nullValue()))
                .andDo(document("route", responseFields(
                        fieldWithPath("request")
                                .type(STRING)
                                .description("Original request details sent to Google maps API"),
                        fieldWithPath("response")
                                .type(STRING)
                                .description("Response JSON obtained from Google Maps API"),
                        fieldWithPath("error").description("Error description, if any"))));
    }

    private static class TestRouteDetails implements RouteDetails<String, String, String> {

        @Override
        public String getRequest() {
            return TEST_REQUEST;
        }

        @Override
        public String getResponse() {
            return TEST_RESPONSE;
        }

        @Override
        public String getError() {
            return null;
        }

    }

    private static String getSampleContent(String fileName) {
        InputStream stream = NutaxiRouteServiceApplicationTests.class.getResourceAsStream("/samples/" + fileName);
        try {
            return IOUtils.toString(stream);
        } catch (IOException exception) {
            throw propagate(exception);
        }
    }

}
