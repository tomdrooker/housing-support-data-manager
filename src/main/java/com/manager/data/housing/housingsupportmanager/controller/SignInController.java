package com.manager.data.housing.housingsupportmanager.controller;

import com.manager.data.housing.housingsupportmanager.model.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class SignInController {

    @GetMapping("sign-in")
    public String getResistration(@ModelAttribute ("sign-in")UserDetails userDetails) {
        return "sign-in";
    }

    @PostMapping("sign-in")
    public String signUserIn(@ModelAttribute ("sign-in")UserDetails userDetails) {
        System.out.printf("Username is %s and password is %s", userDetails.getUsername(), userDetails.getPassword());
        return "sign-in";
    }

}
