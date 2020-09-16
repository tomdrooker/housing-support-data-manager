package com.manager.data.housing.housingsupportmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class FragmentsController {



    @GetMapping("/fragments")
    public String displayFragments(Model model,
                                   Principal principal) {

        String user = principal.getName();

        if (principal.getName() != null) {
            model.addAttribute("user", user);
        }

        return "fragments";
    }

}
