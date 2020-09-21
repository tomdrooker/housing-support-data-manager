package com.manager.data.housing.housingsupportmanager.controller;

import com.manager.data.housing.housingsupportmanager.service.CouncilService;
import com.manager.data.housing.housingsupportmanager.model.Council;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CouncilService service;

    @GetMapping({"/", "/home"})
    public String displayHomePage(Model model) {
        List<Council> councils = service.listAll();
        model.addAttribute("councils", councils);
        return "home";
    }

}