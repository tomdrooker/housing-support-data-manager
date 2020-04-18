package com.manager.data.housing.housingsupportmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FragmentsController {

    @GetMapping("/fragments")
    public String displayFragments() {
        return "fragments";
    }

}
