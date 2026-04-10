package ru.rsreu.sea_fishing.mongo.controllers;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.sea_fishing.mongo.document.UserDocument;
import ru.rsreu.sea_fishing.mongo.repository.UserMongoRepository;

import java.security.Principal;

import static org.springframework.security.core.userdetails.User.withUsername;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

@Profile("mongo")
@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileControllerMongo {

    private final UserMongoRepository userMongoRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String profile(Model model, Principal principal) {
        UserDocument user = userMongoRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден в Mongo по email: " + principal.getName()));

        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/update")
    public String updateProfile(Principal principal, @ModelAttribute("user") UserDocument userForm) {
        UserDocument user;
        if (userForm.getUserId() != null) {
            user = userMongoRepository.findById(userForm.getUserId())
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден по id"));
        } else {
            user = userMongoRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден по email"));
        }

        user.setEmail(userForm.getEmail());
        user.setSurname(userForm.getSurname());
        user.setName(userForm.getName());
        user.setPatronymic(userForm.getPatronymic());

        userMongoRepository.save(user);

        UserDetails updatedUserDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPasswordHash())
                .roles(user.getRole())
                .build();

        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                updatedUserDetails,
                null,
                updatedUserDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        return "redirect:/profile";
    }

    @PostMapping("/change-password")
    public String changePassword(
            Principal principal,
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            Model model
    ) {
        UserDocument user = userMongoRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        if (!passwordEncoder.matches(oldPassword, user.getPasswordHash())) {
            model.addAttribute("user", user);
            model.addAttribute("error", "Неверный старый пароль");
            return "profile";
        }

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userMongoRepository.save(user);

        model.addAttribute("user", user);
        model.addAttribute("success", "Пароль изменён");
        return "profile";
    }
}
