package com.manager.data.housing.housingsupportmanager.controller;

import com.manager.data.housing.housingsupportmanager.model.Council;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CouncilController {

    @GetMapping("/council")
    public Council getCouncil(@RequestParam(value = "councilName", defaultValue = "Sheffield City Council") String councilName,
                              @RequestParam(value = "councilPhone", defaultValue = "0114 2683686") String councilPhone) {

        Council council = new Council();

        council.setCouncilName(councilName);
        council.setCouncilPhone(councilPhone);

        return council;

    }

    @PostMapping("/council")
    public Council postCouncil(Council council) {
        return council;
    }

}
