package com.manager.data.housing.housingsupportmanager.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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

    @WithMockUser(value = "testing", password = "testing123", roles = "ADMIN")
    @Test
    void testDisplayAddCouncil() throws Exception {
        mockMvc.perform(get("/admin/add-new-council"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("council"))
                .andExpect(view().name("admin/add-new-council"));
    }

    @WithMockUser(value = "testing", password = "testing123", roles = "ADMIN")
    @Test
    void testDisplayConfirmDetails() throws Exception {
        Council council = new Council();
        council.setName("Sheffield City Council");

        mockMvc.perform(get("/admin/confirm-new-details")
                .flashAttr("council", council))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("council"))
                    .andExpect(view().name("admin/confirm-new-details"));
    }

    @WithMockUser(value = "testing", password = "testing123", roles = "ADMIN")
    @Test
    void testDisplayEditCouncil() throws Exception {
        given(councilService.get(8L)).willReturn(new Council());

        mockMvc.perform(get("/admin/edit-council-details/{id}", 8L))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("council"))
                .andExpect(view().name("admin/edit-council-details"));
    }

    @Test
    void testDisplayViewCouncil() throws Exception {
        given(councilService.get(12L)).willReturn(new Council());

        mockMvc.perform(get("/view-council/{id}", 12L))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("council"))
                .andExpect(view().name("view-council"));
    }

    @WithMockUser(value = "testing", password = "testing123", roles = "ADMIN")
    @Test
    void testDisplayConfirmDeleteCouncil() throws Exception {
        given(councilService.get(36L)).willReturn(new Council());

        mockMvc.perform(get("/admin/confirm-delete-council/{id}", 36L))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("council"))
                .andExpect(model().attributeExists("councilList"))
                .andExpect(view().name("admin/confirm-delete-council"));
    }

    //Test for successful validation
    @WithMockUser(value = "testing", password = "testing123", roles = "ADMIN")
    @Test
    void testPlaceNewCouncilIntoSession() throws Exception {
        mockMvc.perform(post("/admin/add-new-council")
                .param("name", "Sheffield City Council")
                .param("address", "1 main street")
                .param("email", "housing@sheffield.gov.uk")
                .param("phone", "0114 2345678")
                .param("dhp", "http://www.dhp.co.uk")
                .param("list", "http://www.list.co.uk")
                .param("info", "http://www.info.co.uk")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("councilList"))
                .andExpect(view().name("admin/confirm-new-details"));
    }

    //Test for unsuccessful validation
    @WithMockUser(value = "testing", password = "testing123", roles = "ADMIN")
    @Test
    void testPlaceNewCouncilIntoSessionInvalid() throws Exception {
        mockMvc.perform(post("/admin/add-new-council")
                .param("name", "")
                .with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(view().name("admin/add-new-council"));
    }

    @WithMockUser(value = "testing", password = "testing123", roles = "ADMIN")
    @Test
    void testRouteConfirmationPageForSuccess() throws Exception {
        Council council = new Council();
        council.setName("Sheffield City Council");
        council.setId(9L);
        council.setAddress("1 main street");
        council.setEmail("housing@sheffield.gov.uk");
        council.setPhone("0114 2345678");
        council.setDhp("http://www.dhp.co.uk");
        council.setList("http://www.list.co.uk");
        council.setInfo("http://www.info.co.uk");

        councilList.add(council);

        mockMvc.perform(post("/admin/confirm-new-details")
                .sessionAttr("councilList", councilList)
                .param("confirmation-page-button", "Enter details")
                .with(csrf()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/home"));
    }

    @WithMockUser(value = "testing", password = "testing123", roles = "ADMIN")
    @Test
    void testRouteAmendedConfirmationPage() throws Exception {

        Council council = new Council();
        council.setName("Sheffield City Council");
        council.setAddress("1 main street");
        council.setEmail("housing@sheffield.gov.uk");
        council.setPhone("0114 2345678");
        council.setDhp("http://www.dhp.co.uk");
        council.setList("http://www.list.co.uk");
        council.setInfo("http://www.info.co.uk");

        councilList.add(council);

        mockMvc.perform(post("/admin/confirm-amended-details")
                .param("confirmation-page-button", "Enter details")
                .sessionAttr("councilList", councilList)
                .with(csrf()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/home"));
    }

    @WithMockUser(value = "testing", password = "testing123", roles = "ADMIN")
    @Test
    void testHandleEditCouncilDetails() throws Exception {
        mockMvc.perform(post("/edit-council-details/{id}", 34L)
                .with(csrf()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(flash().attributeExists("councilList"))
                    .andExpect(redirectedUrl("./../admin/confirm-amended-details"));
    }

    @WithMockUser(value = "testing", password = "testing123", roles = "ADMIN")
    @Test
    void testRouteDeletePageForSuccess() throws Exception {
        Council council = new Council();
        council.setName("Sheffield City Council");

        councilList.add(council);

        mockMvc.perform(post("/view-council/delete-council/{id}", 79L)
                .sessionAttr("councilList", councilList)
                .param("confirm-delete-button", "Submit")
                .with(csrf()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/home"));
    }

    @WithMockUser(value = "testing", password = "testing123", roles = "ADMIN")
    @Test
    void testRouteDeletePageForGoBack() throws Exception {
        mockMvc.perform(post("/view-council/delete-council/{id}", 79L)
                    .param("confirm-delete-button", "Go back")
                    .with(csrf()))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl("/view-council/" + 79L));
    }

}
