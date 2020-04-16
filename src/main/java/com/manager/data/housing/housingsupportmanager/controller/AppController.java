package com.manager.data.housing.housingsupportmanager.controller;

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

    @GetMapping("/sign-in")
    public String getSignInCreds(Model model) {
        model.addAttribute("user", new User());
        return "signin";
    }

    @PostMapping("/sign-in")
    public String doSignIn(@ModelAttribute(name="user") User user) {
        System.out.printf("%s, %s", user.getUsername(), user.getPassword());
        return "home";
    }

}
