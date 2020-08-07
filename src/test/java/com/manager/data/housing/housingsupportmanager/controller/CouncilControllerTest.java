package com.manager.data.housing.housingsupportmanager.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.manager.data.housing.housingsupportmanager.service.CouncilService;
import com.manager.data.housing.housingsupportmanager.model.Council;
import com.manager.data.housing.housingsupportmanager.model.CouncilList;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class CouncilControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CouncilService councilService;

    CouncilList councilList = new CouncilList();

    @Test
    void testDisplayAddCouncil() throws Exception {
        mockMvc.perform(get("/add-new-council"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("council"))
                .andExpect(view().name("add-new-council"));
    }

    @Test
    void testDisplaySuccess() throws Exception {
        Council council = new Council();
        council.setName("Sheffield City Council");

        mockMvc.perform(get("/success")
                .requestAttr("action", "add")
                .flashAttr("council", council))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("council"))
                    .andExpect(view().name("success"));
        }

    @Test
    void testDisplayConfirmDetails() throws Exception {
        Council council = new Council();
        council.setName("Sheffield City Council");

        mockMvc.perform(get("/confirm-new-details")
                .flashAttr("council", council))
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
        mockMvc.perform(post("/add-new-council")
                .param("name", ""))
                    .andExpect(status().isOk())
                    .andExpect(view().name("/add-new-council"));
    }

    @Test
    void testRouteConfirmationPageForSuccess() throws Exception {
        Council council = new Council();
        council.setName("Sheffield City Council");

        councilList.add(council);

        mockMvc.perform(post("/confirm-new-details")
                .sessionAttr("councilList", councilList)
                .param("confirmation-page-button", "Submit"))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("action"))
                    .andExpect(view().name("success"));
    }

    @Test
    void testRouteConfirmationPageForChangeDetails() throws Exception {

        Council council = new Council();
        council.setName("Sheffield City Council");

        councilList.add(council);

        mockMvc.perform(post("/confirm-new-details")
                .param("confirmation-page-button", "Change details")
                .sessionAttr("councilList", councilList))
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

        councilList.add(council);

        mockMvc.perform(post("/view-council/delete-council/{id}", 79L)
                .sessionAttr("councilList", councilList)
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
