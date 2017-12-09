package com.aakkus.dc.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestControllerIT {

    private MockMvc mockMvc;
    private MockHttpSession session;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        session = new MockHttpSession();
        session.setAttribute(MockHttpSession.SESSION_COOKIE_NAME, "1");
    }

    @Test
    public void should_execute_method() throws Exception {
        mockMvc.perform(get("/test").session(session))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Test executed."));
    }

    @Test
    public void should_execute_method_when_already_invoked_but_action_is_do_nothing() throws Exception {
        mockMvc.perform(get("/test2").session(session))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Test executed."));

        mockMvc.perform(get("/test2").session(session))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Test executed."));
    }


    @Test
    public void should_not_execute_method_when_already_invoked() throws Exception {
        mockMvc.perform(get("/test").session(session))
                .andDo(print())
                .andExpect(status().isOk());


        Throwable throwable = catchThrowable(() -> mockMvc.perform(get("/test").session(session)));
        assertThat(throwable).isNotNull();
        assertThat(throwable.getCause()).hasMessage("Requested method could not execute for this time!");
    }

    @Test
    public void should_execute_method_when_already_invoked_but_flushed_after() throws Exception {
        mockMvc.perform(get("/test").session(session))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/dc/caches").session(session))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/test").session(session))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void should_execute_method_when_already_invoked_but_flushed_byKey_after() throws Exception {
        mockMvc.perform(get("/test").session(session))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/dc/caches/{key}", "1TestController.getrq()").session(session))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/test").session(session))
                .andDo(print())
                .andExpect(status().isOk());
    }
}