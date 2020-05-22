package com.manager.data.housing.housingsupportmanager.controller;

import com.manager.data.housing.housingsupportmanager.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SignInController {

    @GetMapping("/sign-in")
    public String displaySignIn(Model model) {
        model.addAttribute("user", new User());
        return "sign-in";
    }

    @PostMapping("/sign-in")
    public String doSignIn(@ModelAttribute(name="user") User user) {
        System.out.printf("%s, %s", user.getUsername(), user.getPassword());
        return "home";
    }

}