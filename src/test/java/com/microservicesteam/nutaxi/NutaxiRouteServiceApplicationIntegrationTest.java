package com.microservicesteam.nutaxi;

import static com.microservicesteam.nutaxi.route.RouteFactory.route;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.microservicesteam.nutaxi.route.RouteService;

@RunWith(SpringRunner.class)
@WebMvcTest
@ActiveProfiles("test")
public class NutaxiRouteServiceApplicationIntegrationTest {

    public static final String EXAMPLE_POLYLINE = "{b{`HmgjsBkFdAQcAwAaI}@}EVMfDgAdE_AbBk@";
    
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    @MockBean
    private RouteService mockRouteService;

    @MockBean
    private RedisConnectionFactory redisConnectionFactory;

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
    public void shouldReturnWithRouteWhenGoogleDirectionIsPresent() throws Exception {
        when(mockRouteService.getRoute(anyObject())).thenReturn(Optional.of(route(EXAMPLE_POLYLINE)));

        mockMvc.perform(get("/api/route")
                .accept(APPLICATION_JSON)
                .param("origin", "Budapest Puskin utca 8")
                .param("destination", "Budapest Vas utca 19"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.overviewPolylines[0]", equalTo(EXAMPLE_POLYLINE)))
                .andDo(document("route", 
                        requestParameters(
                                parameterWithName("origin")
                                    .description("Starting point of the requested route"),
                                parameterWithName("destination")
                                    .description("End point of the requested route")),
                        responseFields(
                                fieldWithPath("overviewPolylines")
                                    .type(ARRAY)
                                    .description("Encoded route polylines from Google Maps"))));
    }

    @Test
    public void shouldReturnWithResourceNotFoundWhenGoogleDirectionIsNotPresent() throws Exception {
        when(mockRouteService.getRoute(anyObject())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/route")
                .accept(APPLICATION_JSON)
                .param("origin", "Budapest Puskin utca 8")
                .param("destination", "Budapest Vas utca 19"))
                .andExpect(status().is4xxClientError());
    }

}
