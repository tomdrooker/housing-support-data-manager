package com.manager.data.housing.housingsupportmanager.controller;

import com.manager.data.housing.housingsupportmanager.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@Controller
public class SignInController {

    @GetMapping("/sign-in")
    public String displaySignIn(Model model) {
        model.addAttribute("user", new User());
        return "sign-in";
    }

    @PostMapping("/sign-in")
    public String doSignIn(@Valid @ModelAttribute(name="user") User user,
                           BindingResult bindingResult) {

            if (bindingResult.hasErrors()) {
                return "sign-in";
            }

            System.out.printf("%s, %s", user.getUsername(), user.getPassword());
            return "home";

    }

}