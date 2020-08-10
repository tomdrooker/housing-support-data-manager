package com.manager.data.housing.housingsupportmanager.controller;

import com.manager.data.housing.housingsupportmanager.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@Controller
public class SignInController {

    @GetMapping("/sign-in")
    public String displaySignIn(Model model,
                                HttpServletRequest request) {
        model.addAttribute("user", new User());

        try {
            Object flash = request.getSession().getAttribute("flash");
            model.addAttribute("flash", flash);
            request.getSession().removeAttribute("flash");
        }

        catch (Exception exception) {
            // flash attribute does not exist - do nothing and continue
        }

        return "sign-in";
    }

}