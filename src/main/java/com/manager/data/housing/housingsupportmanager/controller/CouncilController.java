package com.manager.data.housing.housingsupportmanager.controller;

import com.manager.data.housing.housingsupportmanager.FlashMessage;
import com.manager.data.housing.housingsupportmanager.service.CouncilService;
import com.manager.data.housing.housingsupportmanager.model.Council;
import com.manager.data.housing.housingsupportmanager.model.CouncilList;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CouncilService service;

    // Initialise CouncilList to hold council details in session

    @ModelAttribute("councilList")
    public CouncilList createCouncilList() {
        return new CouncilList();
    }

    // Display the add-new-council page

    @GetMapping({"admin/add-new-council", "add-new-council"})
    public String displayAddCouncil(Model model) {
        if (!model.containsAttribute("council")) {
            model.addAttribute("council", new Council());
        }

        return "admin/add-new-council";
    }

    // Display success page when council added successfully - requires an action attribute

    @GetMapping("admin/success")
    public String displaySuccess(Model model,
                                 @ModelAttribute CouncilList councilList) {
        if (!councilList.isEmpty()) {
            model.addAttribute("council", councilList.peekLast());
        }
        return "admin/success";
    }

    // Retrieve council details that have just been added from CouncilList and send them for use on confirm-new-details page
    // Retrieves info from placeNewCouncilIntoSession

    @GetMapping("admin/confirm-new-details")
    public String displayConfirmDetails(Model model,
                                        @ModelAttribute CouncilList councilList) {
        if (!councilList.isEmpty()) {
            model.addAttribute("council", councilList.peekLast());
        }
        return "admin/confirm-new-details";
    }

    // Retrieve council details that have just been added and send them to the confirm-new-details page

    @GetMapping("admin/change-council-details")
    public String displayChangeDetails(Model model,
                                       @ModelAttribute CouncilList councilList) {
        if (!councilList.isEmpty()) {
            model.addAttribute("council", councilList.peekLast());
        }
        return "admin/change-council-details";
    }

    // Retrieve council details from an existing record in the database and display them on the edit-details form

    @GetMapping("admin/edit-council-details/{id}")
    public String displayEditCouncil(Model model,
                                     @PathVariable("id") Long id) {
        model.addAttribute("council", service.get(id));
        return "admin/edit-council-details";
    }

    // Display different view-council page for each link on home page

    @GetMapping("/view-council/{id}")
    public String displayViewCouncil(@PathVariable("id") Long id, Model model) {
        model.addAttribute("council", service.get(id));
        return "view-council";
    }

    //Delete council

    @GetMapping("/admin/confirm-delete-council/{id}")
    public String displayConfirmDeleteCouncil(@PathVariable("id") Long id,
                                              Model model,
                                              @ModelAttribute CouncilList councilList,
                                              RedirectAttributes attributes) {
        Council council = service.get(id);
        model.addAttribute("council", council);
        councilList.add(council);
        attributes.addFlashAttribute("councilList", councilList);
        return "admin/confirm-delete-council";
    }

    // Post maps

    // Collect data from add-new-council form and add to CouncilList and then redirect to confirm-new-details

    @PostMapping("admin/add-new-council")
    public String placeNewCouncilIntoSession(@Valid @ModelAttribute Council council,
                                             BindingResult bindingResult,
                                             @ModelAttribute CouncilList councilList,
                                             RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("council", council);
            return "admin/add-new-council";
        }

        else {
            councilList.add(council);
            attributes.addFlashAttribute("councilList", councilList);
            return "admin/confirm-new-details";
        }
    }

    @PostMapping("/admin/confirm-new-details")
    public String routeConfirmationPage(@RequestParam(name = "confirmation-page-button") String button,
                                          @ModelAttribute("councilList") CouncilList councilList,
                                          Model model,
                                        RedirectAttributes attributes) {

        Council council = councilList.peekLast();
        String page = "";

        if (button.equals("Enter details")) {
            service.save(council);
            attributes.addFlashAttribute("flash", new FlashMessage("Council details added", FlashMessage.Status.SUCCESS));
            model.addAttribute("council", council);
            page = "redirect:/home";
        }

        else if (button.equals("Change details")) {
            model.addAttribute("council", council);
            page = "admin/change-council-details";
        }

        return page;

    }

    @PostMapping("/edit-council-details/{id}")
    public RedirectView handleEditCouncilDetails(@PathVariable("id") int id,
                                           @ModelAttribute Council council,
                                           @ModelAttribute CouncilList councilList,
                                           RedirectAttributes attributes) {
        councilList.add(council);
        attributes.addFlashAttribute("councilList", councilList);
        return new RedirectView("./../admin/confirm-new-details");
    }

    @PostMapping("/view-council/delete-council/{id}")
    public String routeDeletePage(@PathVariable("id") Long id,
                                    @RequestParam(name = "confirm-delete-button") String button,
                                    @ModelAttribute CouncilList councilList,
                                    RedirectAttributes attributes) {

        String page = "";

        if (button.equals("Submit")) {
            service.delete(id);
            attributes.addFlashAttribute("flash", new FlashMessage("Council details deleted", FlashMessage.Status.SUCCESS));
            page = "redirect:/home";
        }

        else if (button.equals("Go back")) {
            page = "redirect:/view-council/" + id;
        }

        return page;

    }
    
}

