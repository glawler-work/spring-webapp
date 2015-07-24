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

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HelloRestControllerTest {
    private HelloRestController helloRestController;
    @Mock private HelloListener helloListener;
    private MockHttpServletRequestBuilder requestBuilder;
    private MockMvc mockMvc;

    @Before
    public void onSetUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        helloRestController = new HelloRestController();
        helloRestController.helloListener = helloListener;
        mockMvc = MockMvcBuilders.standaloneSetup(helloRestController).build();

        requestBuilder = MockMvcRequestBuilders.get("/api/hello");
    }

    @Test
    public void happyPath() throws Exception {
        when(helloListener.getHellos()).thenReturn(Arrays.asList("hello", "hello"));
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
        assertThat("2", is(mvcResult.getResponse().getContentAsString()));
    }
}
