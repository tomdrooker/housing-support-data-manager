package com.manager.data.housing.housingsupportmanager.controller;

import com.manager.data.housing.housingsupportmanager.AppConfig;
import com.manager.data.housing.housingsupportmanager.CouncilService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({ AppConfig.class, HomeController.class})

class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CouncilService councilService;

    @Test
    void testHomePageDisplaysCorrectly() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("councils"))
                .andExpect(view().name("home"));

    }
}