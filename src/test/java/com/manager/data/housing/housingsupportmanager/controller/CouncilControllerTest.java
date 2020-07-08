package com.manager.data.housing.housingsupportmanager.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.manager.data.housing.housingsupportmanager.AppConfig;
import com.manager.data.housing.housingsupportmanager.service.CouncilService;
import com.manager.data.housing.housingsupportmanager.model.Council;
import com.manager.data.housing.housingsupportmanager.model.CouncilList;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.servlet.FlashMap;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest({ AppConfig.class, CouncilController.class })
class CouncilControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CouncilService councilService;

    @MockBean
    CouncilList councilList;

    @Test
    void testDisplayAddCouncil() throws Exception {
        mockMvc.perform(get("/add-new-council"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("council"))
                .andExpect(view().name("add-new-council"));
    }

    @Test
    void testDisplaySuccess() throws Exception {
        mockMvc.perform(get("/success"))
                .andExpect(status().isOk());
    }

    // Requires session attributes to be sent from the
    @Test
    void testDisplayConfirmDetails() throws Exception {
        FlashMap flashMap = mockMvc.perform(post("/add-new-council")
                .param("name", "Sheffield City Council"))
                .andReturn().getFlashMap();

        mockMvc.perform(get("/confirm-new-details")
                .sessionAttrs(flashMap))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("council"))
                .andExpect(view().name("confirm-new-details"));
    }

    @Test
    void testDisplayEditCouncil() throws Exception {
        given(councilService.get(8L)).willReturn(new Council());

        mockMvc.perform(get("/view-council/edit-council-details/{id}", 8L))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("council"))
                .andExpect(view().name("edit-council-details"));
    }

    @Test
    void testDisplayViewCouncil() throws Exception {
        given(councilService.get(12L)).willReturn(new Council());

        mockMvc.perform(get("/view-council/{id}", 12L))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("council"))
                .andExpect(view().name("view-council"));
    }

    @Test
    void testDisplayConfirmDeleteCouncil() throws Exception {
        given(councilService.get(36L)).willReturn(new Council());

        mockMvc.perform(get("/view-council/confirm-delete-council/{id}", 36L))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("council"))
                .andExpect(model().attributeExists("councilList"))
                .andExpect(view().name("confirm-delete-council"));
    }


    //Test for successful validation
    @Test
    void testPlaceNewCouncilIntoSession() throws Exception {
        mockMvc.perform(post("/add-new-council")
            .param("name", "Sheffield City Council"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("councilList"))
                .andExpect(view().name("confirm-new-details"));
    }

    //Test for unsuccessful validation
    @Test
    void testPlaceNewCouncilIntoSessionInvalid() throws Exception {
        mockMvc.perform(post("/add-new-council"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("name"))
                .andExpect(model().attributeHasFieldErrors("name"))
                .andExpect(view().name("add-new-council"));
    }

    @Test
    void testRouteConfirmationPageForSuccess() throws Exception {
        mockMvc.perform(post("/confirm-new-details")
                .param("confirmation-page-button", "Submit"))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("action"))
                    .andExpect(view().name("success"));
    }

    @Test
    void testRouteConfirmationPageForChangeDetails() throws Exception {

        Council council = new Council();
        council.setName("Sheffield City Council");

        mockMvc.perform(post("/confirm-new-details")
                .param("confirmation-page-button", "Change details"))
//                .content(objectMapper.writeValueAsString(council)))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("council"))
                    .andExpect(view().name("change-council-details"));
    }

    @Test
    void testHandleEditCouncilDetails() throws Exception {
        mockMvc.perform(post("/view-council/edit-council-details/{id}", 34L))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("councilList"))
                .andExpect(redirectedUrl("./../../confirm-new-details"));
    }

    @Test
    void testRouteDeletePageForSuccess() throws Exception {

        Council council = new Council();
        council.setName("Sheffield City Council");

        given(councilList.peekLast()).willReturn(council);

        mockMvc.perform(post("/view-council/delete-council/{id}", 79L)
                .param("confirm-delete-button", "Submit"))
                    .andExpect(status().isOk());
    }

    @Test
    void testRouteDeletePageForGoBack() throws Exception {

        mockMvc.perform(post("/view-council/delete-council/{id}", 79L)
                    .param("confirm-delete-button", "Go back"))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl("/view-council/" + 79L));
    }

}
