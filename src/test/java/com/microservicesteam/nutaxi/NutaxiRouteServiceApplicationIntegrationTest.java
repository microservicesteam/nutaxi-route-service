package com.microservicesteam.nutaxi;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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

import com.microservicesteam.nutaxi.route.Route;
import com.microservicesteam.nutaxi.route.RouteService;

@RunWith(SpringRunner.class)
@WebMvcTest
@ActiveProfiles("test")
public class NutaxiRouteServiceApplicationIntegrationTest {

    private static final String DUMMY_POLYLINE = RandomStringUtils.random(10);

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
    public void getRoute() throws Exception {
        when(mockRouteService.getRoute(Mockito.anyObject())).thenReturn(Optional.of(Route.builder()
                .overviewPolylines(newArrayList(DUMMY_POLYLINE))
                .build()));

        mockMvc.perform(get("/api/route")
                .accept(APPLICATION_JSON)
                .param("origin", "Budapest Futó u. 47")
                .param("destination", "Budapest Corvin-negyed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.overviewPolylines[0]", equalTo(DUMMY_POLYLINE)))
                .andDo(document("route", responseFields(
                        fieldWithPath("overviewPolylines")
                                .type(ARRAY)
                                .description("Original request details sent to Google maps API"))));
    }

}
