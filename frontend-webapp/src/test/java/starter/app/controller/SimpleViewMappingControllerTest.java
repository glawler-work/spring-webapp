package starter.app.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import starter.app.listener.HelloListener;
import starter.config.AppInitializer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SimpleViewMappingControllerTest {

    private SimpleViewMappingController simpleViewMappingController;
    @Mock private HelloListener helloListener;
    private MockHttpServletRequestBuilder requestBuilder;
    private MockMvc mockMvc;

    @Before
    public void onSetUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        simpleViewMappingController = new SimpleViewMappingController();

        mockMvc = MockMvcBuilders.standaloneSetup(simpleViewMappingController)
                .setViewResolvers(new AppInitializer().getInternalResourceViewResolver())
                .build();
    }

    @Test
    public void authenticate() throws Exception {
        requestBuilder = MockMvcRequestBuilders.get("/authenticate");
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
        assertThat("authenticate", is(mvcResult.getModelAndView().getViewName()));
    }

    @Test
    public void loggedOut() throws Exception {
        requestBuilder = MockMvcRequestBuilders.get("/loggedOut");
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
        assertThat("loggedOut", is(mvcResult.getModelAndView().getViewName()));
        // investigation required - session seems to be getting recreated
//        HttpSession session = mvcResult.getRequest().getSession(false);
//        assertThat(((MockHttpSession)session).isInvalid(), is(Boolean.TRUE));
    }
}
