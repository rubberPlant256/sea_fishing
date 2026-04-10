package ru.rsreu.sea_fishing.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.sea_fishing.entities.Boat;
import ru.rsreu.sea_fishing.entities.Excursion;
import ru.rsreu.sea_fishing.services.BoatService;
import ru.rsreu.sea_fishing.services.ExcursionService;

import java.util.List;

@Profile("sql")
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ExcursionAdminController {

    private final BoatService boatService;
    private final ExcursionService excursionService;

    @GetMapping("/create-excursion")
    public String showCreateForm(Model model) {
        List<Boat> boats = boatService.findAll();
        model.addAttribute("boats", boats);
        model.addAttribute("excursion", new Excursion());
        return "admin/create_excursion";
    }

    @PostMapping("/create-excursion")
    public String createExcursion(@ModelAttribute Excursion excursion, Model model) {
        Boat selectedBoat = boatService.findById(excursion.getBoat().getBoatId());
        excursion.setBoat(selectedBoat);

        excursionService.save(excursion);
        return "redirect:/admin/excursions";
    }

    @GetMapping("/excursions")
    public String getExcursions(Model model) {
        List<Excursion> excursions = excursionService.findAllFromDate();
        model.addAttribute("excursions", excursions);
        return "admin/excursions";
    }

    @GetMapping("/excursion/delete/{id}")
    public String deleteExcursion(@PathVariable Long id) {
        excursionService.deleteById(id);
        return "redirect:/admin/excursions";
    }
}