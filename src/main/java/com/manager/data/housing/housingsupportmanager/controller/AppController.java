package com.manager.data.housing.housingsupportmanager.controller;

import com.manager.data.housing.housingsupportmanager.model.Council;
import com.manager.data.housing.housingsupportmanager.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class AppController {

    //One controller or many??

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

    //Create council
    @GetMapping("/add-new-council")
    public String viewAddCouncil(Model model) {
        model.addAttribute("council", new Council());
        return "add-new-council";
    }

    @PostMapping("/add-new-council")
    public String addCouncil(@ModelAttribute(name="council") Council council) {
        System.out.printf("%s, %s, %s", council.getCouncilName(), council.getCouncilPhone(), council.getCouncilEmail());
        return "add-new-council"; // Send to confirmation when that page is created
    }

}
