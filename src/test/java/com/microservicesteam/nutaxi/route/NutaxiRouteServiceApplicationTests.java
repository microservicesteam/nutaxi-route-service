package com.microservicesteam.nutaxi.route;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.microservicesteam.nutaxi.route.model.RouteDetails;
import com.microservicesteam.nutaxi.route.service.RouteService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NutaxiRouteServiceApplicationTests {
    
    @MockBean
    private RouteService mockRouteService;
    
    @Autowired
    private MockMvc mvc;

	@SuppressWarnings("rawtypes")
    @Test
	public void aTest() throws Exception {
	    when(mockRouteService.getRoute(anyString(), anyString(), anyString())).thenReturn((RouteDetails) new ARouteDetails());
	    mvc.perform(get("/api/route")
	            .accept(MediaType.APPLICATION_JSON)
	            .param("origin", "testOrigin")
	            .param("destination", "testDestination"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.request", "testRequest").exists())
                .andExpect(jsonPath("$.response", "testResponse").exists())
                .andExpect(jsonPath("$.error", Matchers.nullValue()));
	}
    
    private static class ARouteDetails implements RouteDetails<String, String, String> {

        @Override
        public String getRequest() {
            return "testRequest";
        }

        @Override
        public String getResponse() {
            return "testResponse";
        }

        @Override
        public String getError() {
            return null;
        }
        
    }

}
