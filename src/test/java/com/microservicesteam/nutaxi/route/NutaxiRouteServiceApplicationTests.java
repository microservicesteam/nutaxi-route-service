package com.microservicesteam.nutaxi.route;

import static com.google.common.base.Throwables.propagate;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.JsonFieldType.NULL;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.model.DirectionsResult;
import com.microservicesteam.nutaxi.route.model.GoogleMapsDirectionsRequest;
import com.microservicesteam.nutaxi.route.model.GoogleMapsRouteDetails;
import com.microservicesteam.nutaxi.route.service.GoogleMapsRouteService;

@RunWith(SpringRunner.class)
@WebMvcTest
@ActiveProfiles("test")
public class NutaxiRouteServiceApplicationTests {

    private static final String TEST_REQUEST = getSampleContent("request.json");

    // TODO szogibalu use this instead of null value
    private static final String TEST_RESPONSE = getSampleContent("response.json");

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    @MockBean
    private GoogleMapsRouteService mockRouteService;

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

    @Test
    public void aTest() throws Exception {
        GoogleMapsDirectionsRequest request = new ObjectMapper().readValue(TEST_REQUEST, GoogleMapsDirectionsRequest.class);

        // TODO szogibalu use real value
        DirectionsResult response = null;

        when(mockRouteService.getRoute(anyString(), anyString(), anyString())).thenReturn(GoogleMapsRouteDetails.builder()
                .request(request)
                .response(response)
                .build());
        mockMvc.perform(get("/api/route")
                .accept(APPLICATION_JSON)
                .param("origin", "Budapest Futó u. 47")
                .param("destination", "Budapest Corvin-negyed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.request.origin", equalTo("Budapest Futó u. 47")))
                .andExpect(jsonPath("$.request.destination", equalTo("Budapest Corvin-negyed")))
                .andExpect(jsonPath("$.request.mode", equalTo("DRIVING")))
                .andExpect(jsonPath("$.request.units", equalTo("METRIC")))
                .andExpect(jsonPath("$.request.language", equalTo("hu")))
                .andExpect(jsonPath("$.response", nullValue()))
                .andExpect(jsonPath("$.error", nullValue()))
                .andDo(document("route", responseFields(
                        fieldWithPath("request")
                                .type(OBJECT)
                                .description("Original request details sent to Google maps API"),
                        fieldWithPath("response")
                                .type(NULL)
                                .description("Response JSON obtained from Google Maps API"),
                        fieldWithPath("error").description("Error description, if any"))));
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
