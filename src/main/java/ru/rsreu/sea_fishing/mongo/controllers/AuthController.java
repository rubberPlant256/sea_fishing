package ru.rsreu.sea_fishing.mongo.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.sea_fishing.mongo.document.UserDocument;
import ru.rsreu.sea_fishing.mongo.repository.UserMongoRepository;

@Profile("mongo")
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserMongoRepository userMongoRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(UserDocument user) {
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        user.setRole("USER");

        userMongoRepository.save(user);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
