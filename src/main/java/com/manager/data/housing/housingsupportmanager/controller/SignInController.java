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
public class SignInController {

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

}