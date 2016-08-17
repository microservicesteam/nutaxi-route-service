package com.microservicesteam.nutaxi.route;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
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

    private static final String TEST_RESPONSE = "{\"geocodedWaypoints\":[{\"geocoderStatus\":\"OK\",\"partialMatch\":false,\"placeId\":\"ChIJ5TJDhffcQUcRxyCrsJUvZ60\",\"types\":[\"STREET_ADDRESS\"]},{\"geocoderStatus\":\"OK\",\"partialMatch\":false,\"placeId\":\"ChIJVaa6H1jcQUcRrijwfvTFZ8o\",\"types\":[\"ESTABLISHMENT\",\"POINT_OF_INTEREST\",\"SUBWAY_STATION\",\"TRANSIT_STATION\"]}],\"routes\":[{\"summary\":\"Futó u. és Üllői út/4. út\",\"legs\":[{\"steps\":[{\"htmlInstructions\":\"Haladjon tovább <b>dél</b> felé itt: <b>Futó u.</b>, <b>Üllői út</b>/<b>4. út</b> irányába\",\"distance\":{\"inMeters\":139,\"humanReadable\":\"0,1 km\"},\"duration\":{\"inSeconds\":37,\"humanReadable\":\"1 perc\"},\"startLocation\":{\"lat\":47.4854874,\"lng\":19.0746663},\"endLocation\":{\"lat\":47.4842446,\"lng\":19.0745263},\"steps\":null,\"polyline\":{\"encodedPath\":\"ioy`HuolsBL?|@D~@FlBL\"},\"travelMode\":\"DRIVING\",\"transitDetails\":null},{\"htmlInstructions\":\"Forduljon <b>jobbra</b>, a következő útra: <b>Üllői út</b>/<b>4. út</b><div style=\\\"font-size:0.9em\\\">A cél jobbra lesz</div>\",\"distance\":{\"inMeters\":370,\"humanReadable\":\"0,4 km\"},\"duration\":{\"inSeconds\":81,\"humanReadable\":\"1 perc\"},\"startLocation\":{\"lat\":47.4842446,\"lng\":19.0745263},\"endLocation\":{\"lat\":47.48585869999999,\"lng\":19.0702848},\"steps\":null,\"polyline\":{\"encodedPath\":\"ogy`HynlsB{@hDWhAi@nB{@jDEP_@bBy@fDWTQL\"},\"travelMode\":\"DRIVING\",\"transitDetails\":null}],\"distance\":{\"inMeters\":509,\"humanReadable\":\"0,5 km\"},\"duration\":{\"inSeconds\":118,\"humanReadable\":\"2 perc\"},\"durationInTraffic\":null,\"arrivalTime\":null,\"departureTime\":null,\"startLocation\":{\"lat\":47.4854874,\"lng\":19.0746663},\"endLocation\":{\"lat\":47.48585869999999,\"lng\":19.0702848},\"startAddress\":\"Budapest, Futó u. 47, 1082 Magyarország\",\"endAddress\":\"Budapest, Corvin-negyed, 1085 Magyarország\"}],\"waypointOrder\":[],\"overviewPolyline\":{\"encodedPath\":\"ioy`HuolsBjADlDTsArFeBzGe@tBy@fDWTQL\"},\"bounds\":{\"northeast\":{\"lat\":47.48585869999999,\"lng\":19.0746663},\"southwest\":{\"lat\":47.4842446,\"lng\":19.0702848}},\"copyrights\":\"Térképadatok ©2016 Google\",\"fare\":null,\"warnings\":[]}]}";

    private static final String TEST_REQUEST = "{\"origin\":\"Budapest Futó u. 47\",\"destination\":\"Budapest Corvin-negyed\",\"mode\":\"DRIVING\",\"units\":\"METRIC\",\"language\":\"hu\"}";

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("build/generated-snippets");

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
        when(mockRouteService.getRoute(anyString(), anyString(), anyString())).thenReturn((RouteDetails) new ARouteDetails());
        mockMvc.perform(get("/api/route")
                .accept(MediaType.APPLICATION_JSON)
                .param("origin", "Budapest Futó u. 47")
                .param("destination", "Budapest Corvin-negyed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.request", equalTo(TEST_REQUEST)))
                .andExpect(jsonPath("$.response", equalTo(TEST_RESPONSE)))
                .andExpect(jsonPath("$.error", nullValue()))
                .andDo(document("route", responseFields(
                        fieldWithPath("request")
                                .type(JsonFieldType.STRING)
                                .description("Original request details sent to Google maps API"),
                        fieldWithPath("response")
                                .type(JsonFieldType.STRING)
                                .description("Response JSON obtained from Google Maps API"),
                        fieldWithPath("error").description("Error description, if any"))));
    }

    private static class ARouteDetails implements RouteDetails<String, String, String> {

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

}
