package com.manager.data.housing.housingsupportmanager.controller;

import com.manager.data.housing.housingsupportmanager.model.Council;
import com.manager.data.housing.housingsupportmanager.model.CouncilList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
@SessionAttributes("councilList")
public class CouncilController {

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

    // Retrieve council details from an existing record in the database and display them on the edit-details form

    @GetMapping("/view-council/edit-council-details/{id}")
    public String displayEditCouncil(Model model,
                                     @PathVariable("id") int id) {

        if (id == 1) {
            model.addAttribute("council", new Council("Sheffield Council", "0114 2434565", "sheffield@council.co.uk", 1));
        }

        return "edit-council-details";
    }

    // Display different view-council page for each link on home page

    @GetMapping("/view-council/{id}")
    public String displayViewCouncil(@PathVariable("id") int id,
                                     Model model) {

        if (id == 1) {
            model.addAttribute("council", new Council("Sheffield Council", "0114 2434565", "sheffield@council.co.uk", 1));
        }

        return "view-council";
    }

    //Delete council

    @GetMapping("/view-council/confirm-delete-council/{id}")
    public String displayConfirmDeleteCouncil(@PathVariable("id") int id,
                                              Model model,
                                              @ModelAttribute CouncilList councilList,
                                              @ModelAttribute Council council,
                                              RedirectAttributes attributes) {

        if (id == 1) {
            model.addAttribute("council", new Council("Sheffield Council", "0114 2434565", "sheffield@council.co.uk", 1));
            councilList.add(council);
            attributes.addFlashAttribute("councilList", councilList);
        }

        return "confirm-delete-council";
    }

    // Post maps

    // Collect data from add-new-council form and add to CouncilList and then redirect to confirm-new-details

    @PostMapping("/add-new-council")
    public String placeNewCouncilIntoSession(@Valid @ModelAttribute Council council,
                                             BindingResult bindingResult,
                                             @ModelAttribute CouncilList councilList,
                                             RedirectAttributes attributes) {

        if (bindingResult.hasErrors()) {
            return "add-new-council";
        }

        else {
            councilList.add(council);
            attributes.addFlashAttribute("councilList", councilList);
            return "confirm-new-details";
        }

    }

    @PostMapping("/confirm-new-details")
    public String confirmationPageRouting(@RequestParam(name = "confirmation-page-button") String button,
                                          @ModelAttribute("councilList") CouncilList councilList,
                                          Model model) {

        Council council = councilList.peekLast();
        String page = "";

        if (button.equals("Submit")) {
            model.addAttribute("action", "add");
            page = "success";
            String councilName = council.getCouncilName();
            String councilPhone = council.getCouncilPhone();
            String councilEmail = council.getCouncilEmail();
            System.out.printf("%s, %s, %s", councilName, councilPhone, councilEmail);
        }

        else if (button.equals("Change details")) {
            model.addAttribute("council", council);
            page = "change-council-details";
        }

        return page;

    }

    @PostMapping("/view-council/edit-council-details/{id}")
    public RedirectView handleEditCouncilDetails(@PathVariable("id") int id,
                                           @ModelAttribute Council council,
                                           @ModelAttribute CouncilList councilList,
                                           RedirectAttributes attributes) {
        councilList.add(council);
        attributes.addFlashAttribute("councilList", councilList);
        return new RedirectView("./../../confirm-new-details");
    }

    @PostMapping("/view-council/delete-council/{id}")
    public String deletePageRouting(@PathVariable("id") int id,
                                    @RequestParam(name = "confirm-delete-button") String button,
                                    @RequestHeader(value = "referer", required = false) String referer,
                                    Model model) {

        String page = "";
        //should be able to get entry from database using id from url here eventually
        model.addAttribute("council", new Council("Sheffield Council", "0114 2434565", "sheffield@council.co.uk", 1));

        if (button.equals("Submit")) {
            model.addAttribute("action", "delete");
            page = "success";
            System.out.println(id);
        }

        else if (button.equals("Go back")) {
            page = "redirect:/view-council/" + id;
        }

        return page;

    }
    
}

