package ru.rsreu.sea_fishing.controllers.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.sea_fishing.dto.BoatFormDto;
import ru.rsreu.sea_fishing.entities.Boat;
import ru.rsreu.sea_fishing.services.BoatService;

@Profile("sql")
@Controller
@RequestMapping("/admin/boats")
@RequiredArgsConstructor
public class BoatAdminController {

    private final BoatService boatService;

    @GetMapping
    public String listBoats(Model model) {
        model.addAttribute("boats", boatService.findAll());
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

        Boat boat = new Boat();

        String boatIdStr = boatDto.boatId();
        if (boatIdStr != null && !boatIdStr.isBlank()) {
            try {
                boat.setBoatId(Integer.valueOf(boatIdStr));
            } catch (NumberFormatException e) {
                bindingResult.rejectValue("boatId", "boatId.invalid", "Некорректный boatId");
                model.addAttribute("boat", boatDto);
                return "admin/boat_form";
            }
        }

        boat.setBoatName(boatDto.boatName());
        boat.setCapacity(boatDto.capacity());
        boat.setPricePerHour(boatDto.pricePerHour());

        boatService.save(boat);
        return "redirect:/admin/boats";
    }

    @GetMapping("/delete/{id}")
    public String deleteBoat(@PathVariable Integer id) {
        boatService.deleteById(id);
        return "redirect:/admin/boats";
    }
}
