package ru.rsreu.sea_fishing.mongo.controllers.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.sea_fishing.dto.BoatFormDto;
import ru.rsreu.sea_fishing.mongo.document.BoatDocument;
import ru.rsreu.sea_fishing.mongo.services.BoatMongoService;

import java.util.List;

@Profile("mongo")
@Controller
@RequestMapping("/admin/boats")
@RequiredArgsConstructor
public class MongoBoatAdminController {

    private final BoatMongoService boatMongoService;

    @GetMapping
    public String listBoats(Model model) {
        List<BoatDocument> boats = boatMongoService.findAll();
        model.addAttribute("boats", boats);
        return "admin/boats";
    }

    @GetMapping("/add")
    public String addBoatForm(Model model) {
        model.addAttribute("boat", new BoatFormDto(null, null, null, null));
        return "admin/boat_form";
    }

    @PostMapping("/save")
    public String saveBoat(
            @ModelAttribute("boat") @Valid BoatFormDto boatDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("boat", boatDto);
            return "admin/boat_form";
        }

        boatMongoService.save(boatDto);
        return "redirect:/admin/boats";
    }

    @GetMapping("/delete/{id}")
    public String deleteBoat(@PathVariable String id) {
        boatMongoService.deleteById(id);
        return "redirect:/admin/boats";
    }
}
