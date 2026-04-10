package ru.rsreu.sea_fishing.controllers.admin;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.sea_fishing.dto.FishFormDto;
import ru.rsreu.sea_fishing.entities.FishType;
import ru.rsreu.sea_fishing.services.FishService;

@Profile("sql")
@Controller
@RequestMapping("/admin/fishes")
@RequiredArgsConstructor
public class FishAdminController {

    private final FishService fishService;

    @GetMapping
    public String listFish(Model model) {
        model.addAttribute("fishes", fishService.findAll());
        return "admin/fishes";
    }

    @GetMapping("/add")
    public String addFishForm(Model model) {
        model.addAttribute("fish", new FishFormDto(null, null, null));
        return "admin/fish_form";
    }

    @PostMapping("/save")
    public String saveFish(
            @ModelAttribute("fish") @Valid FishFormDto fishDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/fish_form";
        }

        FishType fish = new FishType();

        String fishIdStr = fishDto.fishId();
        if (fishIdStr != null && !fishIdStr.isBlank()) {
            try {
                fish.setFishId(Integer.valueOf(fishIdStr));
            } catch (NumberFormatException e) {
                bindingResult.rejectValue("fishId", "fishId.invalid", "Некорректный fishId");
                return "admin/fish_form";
            }
        }

        fish.setFishName(fishDto.fishName());
        fish.setDifficultyLevel(fishDto.difficultyLevel());

        fishService.save(fish);
        return "redirect:/admin/fishes";
    }


    @GetMapping("/delete/{id}")
    public String deleteFish(@PathVariable Integer id) {
        fishService.deleteById(id);
        return "redirect:/admin/fishes";
    }
}
