package ru.rsreu.sea_fishing.mongo.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.sea_fishing.dto.BookingFormDto;
import ru.rsreu.sea_fishing.mongo.document.*;
import ru.rsreu.sea_fishing.mongo.dto.*;
import ru.rsreu.sea_fishing.mongo.repository.UserMongoRepository;
import ru.rsreu.sea_fishing.mongo.services.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Profile("mongo")
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class BookingControllerMongo {

    private final BookingMongoService bookingMongoService;
    private final ExcursionMongoService excursionMongoService;
    private final FishingSpotMongoService fishingSpotMongoService;
    private final BoatMongoService boatMongoService;
    private final FishMongoService fishMongoService;
    private final UserMongoRepository userMongoRepository;

    @GetMapping("/booking-add")
    public String bookingPage(Model model) {
        List<ExcursionDocument> excursions = excursionMongoService.findAllFromDate();
        List<FishingSpotDocument> spots = fishingSpotMongoService.findAll();

        Map<String, BoatDocument> boatMap = boatMongoService.findAll().stream()
                .collect(Collectors.toMap(b -> b.getBoatId().toHexString(), b -> b));

        Map<String, FishTypeDocument> fishMap = fishMongoService.findAll().stream()
                .collect(Collectors.toMap(f -> f.getFishId().toHexString(), f -> f));

        List<ExcursionView> excursionViews = excursions.stream().map(e -> {
            BoatDocument boatDoc = boatMap.get(e.getBoatId().toHexString());

            BoatView boatView = new BoatView(
                    e.getBoatId().toHexString(),
                    boatDoc != null ? boatDoc.getBoatName() : "Unknown",
                    boatDoc != null ? boatDoc.getCapacity() : null,
                    boatDoc != null ? boatDoc.getPricePerHour() : null
            );

            return new ExcursionView(
                    e.getExcursionId().toHexString(),
                    boatView,
                    e.getStartTime(),
                    e.getEndTime()
            );
        }).toList();

        List<FishingSpotView> spotViews = spots.stream().map(s -> {
            List<SpotFaunaView> faunaViews = Optional.ofNullable(s.getFauna()).orElse(List.of())
                    .stream()
                    .map(this::toSpotFaunaView)
                    .toList();

            return new FishingSpotView(
                    s.getSpotId().toHexString(),
                    s.getSpotName(),
                    s.getDepthMeters(),
                    s.getBottomType(),
                    new HashSet<>(faunaViews)
            );
        }).toList();

        model.addAttribute("excursions", excursionViews);
        model.addAttribute("spots", spotViews);
        model.addAttribute("booking", new BookingFormDto(null, null));
        return "booking_add";
    }

    private SpotFaunaView toSpotFaunaView(SpotFaunaEmbedded sf) {
        throw new UnsupportedOperationException("Метод используется только внутри bookingPage с fishMap.");
    }

    @PostMapping("/booking/save")
    public String saveBooking(@ModelAttribute BookingFormDto bookingForm,
                                @AuthenticationPrincipal UserDetails userDetails) {
        bookingMongoService.saveBooking(userDetails.getUsername(), bookingForm);
        return "redirect:/user/booking?success";
    }

    @GetMapping("/booking")
    public String myBookings(Model model,
                               @AuthenticationPrincipal UserDetails userDetails) {
        List<BookingDocument> bookings = bookingMongoService.getBookingsForCurrentUser(userDetails.getUsername());

        Map<String, BoatDocument> boatMap = boatMongoService.findAll().stream()
                .collect(Collectors.toMap(b -> b.getBoatId().toHexString(), b -> b));
        Map<String, FishTypeDocument> fishMap = fishMongoService.findAll().stream()
                .collect(Collectors.toMap(f -> f.getFishId().toHexString(), f -> f));
        Map<String, FishingSpotDocument> spotMap = fishingSpotMongoService.findAll().stream()
                .collect(Collectors.toMap(s -> s.getSpotId().toHexString(), s -> s));
        Map<String, ExcursionDocument> excursionMap = excursionMongoService.findAllFromDate().stream()
                .collect(Collectors.toMap(e -> e.getExcursionId().toHexString(), e -> e));

        List<BookingView> bookingViews = bookings.stream().map(b -> {
            ExcursionDocument e = excursionMap.get(b.getExcursionId().toHexString());
            FishingSpotDocument s = spotMap.get(b.getSpotId().toHexString());

            BoatDocument boatDoc = boatMap.get(e.getBoatId().toHexString());
            BoatView boatView = new BoatView(
                    e.getBoatId().toHexString(),
                    boatDoc != null ? boatDoc.getBoatName() : "Unknown",
                    boatDoc != null ? boatDoc.getCapacity() : null,
                    boatDoc != null ? boatDoc.getPricePerHour() : null
            );

            ExcursionView excursionView = new ExcursionView(
                    e.getExcursionId().toHexString(),
                    boatView,
                    e.getStartTime(),
                    e.getEndTime()
            );

            List<SpotFaunaView> faunaViews = Optional.ofNullable(s.getFauna()).orElse(List.of())
                    .stream()
                    .map(sf -> {
                        FishTypeDocument fishDoc = fishMap.get(sf.getFishId().toHexString());
                        FishView fishView = new FishView(
                                sf.getFishId().toHexString(),
                                fishDoc != null ? fishDoc.getFishName() : "Unknown"
                        );
                        return new SpotFaunaView(fishView, sf.getSeasonality());
                    })
                    .toList();

            FishingSpotView spotView = new FishingSpotView(
                    s.getSpotId().toHexString(),
                    s.getSpotName(),
                    s.getDepthMeters(),
                    s.getBottomType(),
                    new HashSet<>(faunaViews)
            );


            var userDoc = userMongoRepository.findById(b.getUserId())
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

            var userView = new UserView(userDoc.getEmail());

            return new BookingView(
                    b.getBookingId().toHexString(),
                    userView,
                    excursionView,
                    spotView
            );

        }).toList();

        model.addAttribute("bookings", bookingViews);
        return "booking";
    }

    @PostMapping("/bookings/{bookingId}/cancel")
    public String cancelBooking(@PathVariable String bookingId,
                                  @AuthenticationPrincipal UserDetails userDetails) {
        bookingMongoService.cancelBookingById(bookingId, userDetails.getUsername());
        return "redirect:/user/booking?canceled";
    }
}
