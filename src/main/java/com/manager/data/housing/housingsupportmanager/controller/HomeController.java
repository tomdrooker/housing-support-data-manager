package com.manager.data.housing.housingsupportmanager.controller;

import com.manager.data.housing.housingsupportmanager.model.Council;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String displayHomePage(Model model) {
        List<Council> councils = new ArrayList<Council>();
        councils.add(new Council("Sheffield Council", "0114 2434565", "sheffield@council.co.uk", 1));
        councils.add(new Council("Leeds Council", "0113 3432456", "leeds@council.co.uk", 2));
        model.addAttribute("councils", councils);
        return "home";
    }

}
