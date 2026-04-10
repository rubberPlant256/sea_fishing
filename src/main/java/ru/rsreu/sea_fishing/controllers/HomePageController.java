package ru.rsreu.sea_fishing.controllers;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Profile("sql")
@Controller
public class HomePageController {

    @GetMapping("/")
    public String homePage() {
        return "main";
    }


}
