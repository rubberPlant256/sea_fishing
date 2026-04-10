package ru.rsreu.sea_fishing.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.sea_fishing.entities.Booking;
import ru.rsreu.sea_fishing.services.BookingService;

import java.util.List;

@Profile("sql")
@Controller
@RequestMapping("/admin/bookings")
@RequiredArgsConstructor
public class BookingAdminController {

    private final BookingService bookingService;

    @GetMapping
    public String listBookings(Model model) {
        List<Booking> bookings = bookingService.getAllBookingsForAdmin();
        model.addAttribute("bookings", bookings);
        return "admin/bookings";
    }

    @PostMapping("/{bookingId}/cancel")
    public String cancelBooking(@PathVariable Integer bookingId) {
        bookingService.cancelBookingById(bookingId);
        return "redirect:/admin/bookings?canceled";
    }
}
