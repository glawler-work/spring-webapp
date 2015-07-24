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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HelloControllerTest {

    private static final String INDEX = "index";
    private HelloController helloController;
    @Mock
    private HelloListener helloListener;
    private MockHttpServletRequestBuilder requestBuilder;
    private MockMvc mockMvc;

    @Before
    public void onSetUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        helloController = new HelloController();
        helloController.helloListener = helloListener;
        mockMvc = MockMvcBuilders.standaloneSetup(helloController).build();

        requestBuilder = MockMvcRequestBuilders.get("/hello");
    }

    @Test
    public void upgradeSuccess() throws Exception {
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
        assertThat(INDEX, is(mvcResult.getModelAndView().getViewName()));
    }
}
