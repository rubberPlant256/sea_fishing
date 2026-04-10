package ru.rsreu.sea_fishing.controllers.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.sea_fishing.dto.FishingSpotFormDto;
import ru.rsreu.sea_fishing.dto.SpotFaunaFormDto;
import ru.rsreu.sea_fishing.services.FishService;
import ru.rsreu.sea_fishing.services.FishingSpotService;

@Profile("sql")
@Controller
@RequestMapping("/admin/spots")
@RequiredArgsConstructor
public class FishingSpotAdminController {

    private final FishingSpotService fishingSpotService;
    private final FishService fishService;

    @GetMapping
    public String listSpots(Model model) {
        model.addAttribute("spots", fishingSpotService.findAll());
        model.addAttribute("fishes", fishService.findAll());
        return "admin/spots";
    }

    @GetMapping("/add")
    public String addSpotForm(Model model) {
        FishingSpotFormDto spotForm = new FishingSpotFormDto();
        spotForm.getFauna().add(new SpotFaunaFormDto());
        model.addAttribute("spotForm", spotForm);
        model.addAttribute("fishes", fishService.findAll());
        return "admin/spot_form";
    }

    @PostMapping("/save")
    public String saveSpot(
            @ModelAttribute("spotForm") @Valid FishingSpotFormDto spotForm,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("spotForm", spotForm);
            model.addAttribute("fishes", fishService.findAll());
            return "admin/spot_form";
        }

        fishingSpotService.save(spotForm);
        return "redirect:/admin/spots";
    }

    @PostMapping("/{spotId}/fauna/add")
    public String addFauna(@PathVariable Integer spotId,
                           @RequestParam Integer fishId,
                           @RequestParam String seasonality) {
        fishingSpotService.addFauna(spotId, fishId, seasonality);
        return "redirect:/admin/spots";
    }

    @GetMapping("/{spotId}/fauna/delete/{fishId}")
    public String deleteFauna(@PathVariable Integer spotId, @PathVariable Integer fishId) {
        fishingSpotService.deleteFauna(spotId, fishId);
        return "redirect:/admin/spots";
    }

    @GetMapping("/delete/{id}")
    public String deleteSpot(@PathVariable Integer id) {
        fishingSpotService.deleteById(id);
        return "redirect:/admin/spots";
    }
}
