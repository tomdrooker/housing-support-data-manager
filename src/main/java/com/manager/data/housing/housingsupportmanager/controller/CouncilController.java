package com.manager.data.housing.housingsupportmanager.controller;

import com.manager.data.housing.housingsupportmanager.model.Council;
import com.manager.data.housing.housingsupportmanager.model.CouncilList;
import com.manager.data.housing.housingsupportmanager.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@SessionAttributes("councilList")
public class CouncilController {

    // Initialise ArrayDeque to hold council details in session

    @ModelAttribute("councilList")
    public CouncilList createCouncilList() {
        return new CouncilList();
    }

    // Display pages

    @GetMapping("/add-new-council")
    public String displayAddCouncil(Model model) {
        model.addAttribute("council", new Council());
        return "add-new-council";
    }

    @GetMapping("/success")
    public String displaySuccess() {
        return "success";
    }

    @GetMapping("/confirm-new-details")
    public String displayConfirmDetails(Model model,
                                        @ModelAttribute("councilList") CouncilList councilList) {
        if (!councilList.isEmpty()) {
            model.addAttribute("council", councilList.peekLast());
        }
        return "confirm-new-details";
    }

    // Post maps

    @PostMapping("/add-new-council")
    public RedirectView placeNewCouncilIntoSession(
            @ModelAttribute Council council,
            @ModelAttribute("councilList") CouncilList councilList,
            RedirectAttributes attributes) {
        councilList.add(council);
        attributes.addFlashAttribute("councilList", councilList);
        return new RedirectView("./confirm-new-details");
    }

    @PostMapping("/confirm-new-details")
    public String confirmationPageRouting(@RequestParam(name = "confirmation-page-button") String button,
                                          @ModelAttribute("councilList") CouncilList councilList) {

        String page = "";

        if (button.equals("Submit")) {
            page = "success";
            Council council = councilList.peekLast();
            String councilName = council.getCouncilName();
            String councilPhone = council.getCouncilPhone();
            String councilEmail = council.getCouncilEmail();
            System.out.printf("%s, %s, %s", councilName, councilPhone, councilEmail);
        } // else if button.equals "Change details" go to the edit council bit
        return page;
    }
    
}

