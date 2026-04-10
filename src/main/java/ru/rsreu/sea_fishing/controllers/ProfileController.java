package ru.rsreu.sea_fishing.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.sea_fishing.entities.User;
import ru.rsreu.sea_fishing.repositories.UserRepository;

import java.security.Principal;

@Profile("sql")
@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String profile(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/update")
    public String updateProfile(@ModelAttribute User userForm) {
        User user = userRepository.findById(userForm.getUserId()).orElseThrow();

        user.setEmail(userForm.getEmail());
        user.setSurname(userForm.getSurname());
        user.setName(userForm.getName());
        user.setPatronymic(userForm.getPatronymic());

        userRepository.save(user);

        return "redirect:/profile";
    }

    @PostMapping("/change-password")
    public String changePassword(Principal principal,
                                 @RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 Model model) {

        User user = userRepository.findByEmail(principal.getName()).orElseThrow();

        if (!passwordEncoder.matches(oldPassword, user.getPasswordHash())) {
            model.addAttribute("user", user);
            model.addAttribute("error", "Неверный старый пароль");
            return "profile";
        }

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        model.addAttribute("user", user);
        model.addAttribute("success", "Пароль изменён");

        return "profile";
    }
}