package com.manager.data.housing.housingsupportmanager.controller;

import com.manager.data.housing.housingsupportmanager.model.Council;
import com.manager.data.housing.housingsupportmanager.model.CouncilList;
import com.manager.data.housing.housingsupportmanager.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@Controller
@SessionAttributes("councilList")
public class AppController {

    @GetMapping("/home")
    public String viewHomePage(Map<String, Object> model) {
        return "home";
    }

    // Sign in

    @GetMapping("/sign-in")
    public String viewSignIn(Model model) {
        model.addAttribute("user", new User());
        return "sign-in";
    }

    @PostMapping("/sign-in")
    public String doSignIn(@ModelAttribute(name="user") User user) {
        System.out.printf("%s, %s", user.getUsername(), user.getPassword());
        return "home";
    }

    // Create council

    @GetMapping("/add-new-council")
    public String viewAddCouncil(Model model) {
        model.addAttribute("council", new Council());
        return "add-new-council";
    }

    @ModelAttribute("councilList")
    public CouncilList createCouncilList() {
        return new CouncilList();
    }

    @PostMapping("/add-new-council")
    public RedirectView create(
            @ModelAttribute Council council,
            @ModelAttribute("councilList") CouncilList councilList,
            RedirectAttributes attributes) {
        councilList.add(council);
        attributes.addFlashAttribute("councilList", councilList);
        return new RedirectView("./confirm-new-details");
    }

    @GetMapping("/confirm-new-details")
    public String showForm(Model model,
                           @ModelAttribute("councilList") CouncilList councilList) {
        if (!councilList.isEmpty()) {
            model.addAttribute("council", councilList.peekLast());
        }
        return "confirm-new-details";
    }


    @GetMapping("/success")
    public String displaySuccess(Model model) {
        return "success";
    }

    @PostMapping("/confirm-new-details")
    public String confirmationPageRouting(@RequestParam(name = "confirmation-page-button") String button, @ModelAttribute("councilList") CouncilList councilList) {

        String page = "";

        if (button.equals("Submit")){
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