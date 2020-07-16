package com.manager.data.housing.housingsupportmanager.controller;

import com.manager.data.housing.housingsupportmanager.service.CouncilService;
import com.manager.data.housing.housingsupportmanager.model.Council;
import com.manager.data.housing.housingsupportmanager.model.CouncilList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

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
        if (!model.containsAttribute("council")) {
            model.addAttribute("council", new Council());
        }

        return "add-new-council";
    }

    // Display success page when council added successfully - requires an action attribute

    @GetMapping("/success")
    public String displaySuccess(Model model,
                                 @ModelAttribute CouncilList councilList) {
        if (!councilList.isEmpty()) {
            model.addAttribute("council", councilList.peekLast());
        }
        return "success";
    }

    // Retrieve council details that have just been added from CouncilList and send them for use on confirm-new-details page
    // Retrieves info from placeNewCouncilIntoSession

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
                                     @PathVariable("id") Long id) {

        model.addAttribute("council", service.get(id));

        return "edit-council-details";
    }

    // Display different view-council page for each link on home page

    @GetMapping("/view-council/{id}")
    public String displayViewCouncil(@PathVariable("id") Long id, Model model) {

        model.addAttribute("council", service.get(id));

        return "view-council";

    }

    //Delete council

    @GetMapping("/view-council/confirm-delete-council/{id}")
    public String displayConfirmDeleteCouncil(@PathVariable("id") Long id,
                                              Model model,
                                              @ModelAttribute CouncilList councilList,
                                              RedirectAttributes attributes) {
        Council council = service.get(id);
        model.addAttribute("council", council);
        councilList.add(council);
        attributes.addFlashAttribute("councilList", councilList);

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
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.council", bindingResult);
            attributes.addFlashAttribute("council", council);
            return "redirect:/add-new-council";
        }

        else {
            councilList.add(council);
            attributes.addFlashAttribute("councilList", councilList);
            return "confirm-new-details";
        }
    }

    @PostMapping("/confirm-new-details")
    public String routeConfirmationPage(@RequestParam(name = "confirmation-page-button") String button,
                                          @ModelAttribute("councilList") CouncilList councilList,
                                          Model model) {

        Council council = councilList.peekLast();
        String page = "";

        if (button.equals("Submit")) {
            service.save(council);
            model.addAttribute("action", "add");
            model.addAttribute("council", council);
            page = "success";
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
    public String routeDeletePage(@PathVariable("id") Long id,
                                    @RequestParam(name = "confirm-delete-button") String button,
                                    @ModelAttribute CouncilList councilList,
                                    Model model) {

        String page = "";

        if (button.equals("Submit")) {
            service.delete(id);
            model.addAttribute("action", "delete");
            model.addAttribute("council", councilList.peekLast());
            page = "success";
        }

        else if (button.equals("Go back")) {
            page = "redirect:/view-council/" + id;
        }

        return page;

    }
    
}

