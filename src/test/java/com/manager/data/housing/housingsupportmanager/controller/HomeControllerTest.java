package com.manager.data.housing.housingsupportmanager.controller;

import com.manager.data.housing.housingsupportmanager.service.CouncilService;
import com.manager.data.housing.housingsupportmanager.model.Council;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CouncilService councilService;

    List<Council> councils = new ArrayList<>();

    Council council = new Council();

    @BeforeEach
    void setUp() {
        council.setName("Sheffield City Council");
        councils.add(council);
        given(councilService.listAll()).willReturn(councils);
    }

    @Test
    void testDisplayHomePage() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("councils"))
                .andExpect(view().name("home"));

//        ArgumentCaptor<Council> councilArgumentCaptor = ArgumentCaptor.forClass(Council.class);

//        assertThat(councilArgumentCaptor.getValue().getName()).isEqualTo("Sheffield City Council");
    }

    @Test
    void testListAllMethodReturnsCorrectValue() throws Exception {
        MvcResult result = mockMvc.perform(get("/home"))
                .andReturn();

        verify(councilService, times(1)).listAll();

//        String actualResponseBody = result.getResponse().getContentAsString().;
//        String expectedResponseBody = councils.get(0).getName();
//
//
//
//        System.out.println("Expected: " + expectedResponseBody);

    }
}