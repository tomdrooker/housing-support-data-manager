package com.manager.data.housing.housingsupportmanager.controller;

import com.manager.data.housing.housingsupportmanager.CouncilService;
import com.manager.data.housing.housingsupportmanager.model.Council;
import com.manager.data.housing.housingsupportmanager.model.CouncilList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@SessionAttributes("councilList")
public class CouncilController {

    @Autowired
    private CouncilService service;

    // Initialise CouncilList to hold council details in session

    @ModelAttribute("councilList")
    public CouncilList createCouncilList() {
        return new CouncilList();
    }

    // Display the add-new-council page

    @GetMapping("/add-new-council")
    public String displayAddCouncil(Model model) {
        model.addAttribute("council", new Council());
        return "add-new-council";
    }

    // Display success page when council added successfully

    @GetMapping("/success")
    public String displaySuccess() {
        return "success";
    }

    // Retrieve council details that have just been added from CouncilList and send them for use on confirm-new-details page

    @GetMapping("/confirm-new-details")
    public String displayConfirmDetails(Model model,
                                        @ModelAttribute CouncilList councilList) {
        if (!councilList.isEmpty()) {
            model.addAttribute("council", councilList.peekLast());
        }
        return "confirm-new-details";
    }

    // Retrieve council details that have just been added and send them to the confirm-new-details page

    @GetMapping("/change-council-details")
    public String displayChangeDetails(Model model,
                                       @ModelAttribute CouncilList councilList) {
        if (!councilList.isEmpty()) {
            model.addAttribute("council", councilList.peekLast());
        }
        return "confirm-new-details";
    }

    // Display different view-council page for each link on home page

    @GetMapping("/view-council/{id}")
    public String displayViewCouncil(@PathVariable("id") Long id, Model model) {

        model.addAttribute("council", service.get(id));

        return "view-council";

    }

    // Post maps

    // Collect data from add-new-council form and add to CouncilList and then redirect to confirm-new-details

    @PostMapping("/add-new-council")
    public RedirectView placeNewCouncilIntoSession(
            @ModelAttribute Council council,
            @ModelAttribute CouncilList councilList,
            RedirectAttributes attributes) {
        System.out.println(council.getName());
        councilList.add(council);
        attributes.addFlashAttribute("councilList", councilList);
        return new RedirectView("./confirm-new-details");
    }

    @PostMapping("/confirm-new-details")
    public String confirmationPageRouting(@RequestParam(name = "confirmation-page-button") String button,
                                          @ModelAttribute("councilList") CouncilList councilList,
                                          Model model) {

        Council council = councilList.peekLast();
        String page = "";

        if (button.equals("Submit")) {
            service.save(council);
            page = "success";
        }

        else if (button.equals("Change details")) {
            model.addAttribute("council", council);
            page = "change-council-details";
        }

        return page;

    }
    
}

