package ru.rsreu.sea_fishing.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.sea_fishing.dto.BookingFormDto;
import ru.rsreu.sea_fishing.entities.Booking;
import ru.rsreu.sea_fishing.entities.User;
import ru.rsreu.sea_fishing.repositories.UserRepository;
import ru.rsreu.sea_fishing.services.BookingService;

import java.util.List;

@Profile("sql")
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final UserRepository userRepository;

    @GetMapping("/booking-add")
    public String bookingPage(Model model) {
        model.addAttribute("excursions", bookingService.getAllExcursions());
        model.addAttribute("spots", bookingService.getAllSpots());
        model.addAttribute("booking", new BookingFormDto(null, null)); // excursionId, spotId
        return "booking_add";
    }

    @PostMapping("/booking/save")
    public String saveBooking(@ModelAttribute BookingFormDto bookingForm,
                              @AuthenticationPrincipal UserDetails userDetails) {

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        bookingService.saveBooking(
                user,
                Integer.valueOf(bookingForm.excursionId()),
                Integer.valueOf(bookingForm.spotId())
        );

        return "redirect:/user/booking?success";
    }

    @GetMapping("/booking")
    public String myBookings(
            Model model,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        List<Booking> bookings = bookingService.getBookingsForCurrentUser(userDetails.getUsername());
        model.addAttribute("bookings", bookings);
        return "booking";
    }

    @PostMapping("/bookings/{bookingId}/cancel")
    public String cancelBooking(
            @PathVariable Integer bookingId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        bookingService.cancelBooking(bookingId, userDetails.getUsername());
        return "redirect:/user/booking?canceled";
    }
}
