package ru.rsreu.sea_fishing.mongo.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.sea_fishing.mongo.document.BookingDocument;
import ru.rsreu.sea_fishing.mongo.document.ExcursionDocument;
import ru.rsreu.sea_fishing.mongo.document.FishingSpotDocument;
import ru.rsreu.sea_fishing.mongo.document.FishTypeDocument;
import ru.rsreu.sea_fishing.mongo.document.UserDocument;
import ru.rsreu.sea_fishing.mongo.dto.*;
import ru.rsreu.sea_fishing.mongo.repository.UserMongoRepository;
import ru.rsreu.sea_fishing.mongo.services.*;

import java.util.*;
import java.util.stream.Collectors;

@Profile("mongo")
@Controller
@RequestMapping("/admin/bookings")
@RequiredArgsConstructor
public class MongoBookingAdminController {

    private final BookingMongoService bookingMongoService;
    private final ExcursionMongoService excursionMongoService;
    private final FishingSpotMongoService fishingSpotMongoService;
    private final BoatMongoService boatMongoService;
    private final FishMongoService fishMongoService;
    private final UserMongoRepository userMongoRepository;

    @GetMapping
    public String listBookings(Model model) {
        List<BookingDocument> bookings = bookingMongoService.getAllBookingsForAdmin();

        Map<String, ru.rsreu.sea_fishing.mongo.document.BoatDocument> boatMap =
                boatMongoService.findAll().stream()
                        .collect(Collectors.toMap(b -> b.getBoatId().toHexString(), b -> b));

        Map<String, FishingSpotDocument> spotMap =
                fishingSpotMongoService.findAll().stream()
                        .collect(Collectors.toMap(s -> s.getSpotId().toHexString(), s -> s));

        Map<String, ExcursionDocument> excursionMap =
                excursionMongoService.findAllFromDate().stream()
                        .collect(Collectors.toMap(e -> e.getExcursionId().toHexString(), e -> e));

        Map<String, FishTypeDocument> fishMap =
                fishMongoService.findAll().stream()
                        .collect(Collectors.toMap(f -> f.getFishId().toHexString(), f -> f));

        List<BookingView> viewList = bookings.stream().map(b -> {
            UserDocument user = userMongoRepository.findById(b.getUserId()).orElseThrow();

            ExcursionDocument e = excursionMap.get(b.getExcursionId().toHexString());
            FishingSpotDocument s = spotMap.get(b.getSpotId().toHexString());
            var boatDoc = boatMap.get(e.getBoatId().toHexString());

            ExcursionView excursionView = new ExcursionView(
                    e.getExcursionId().toHexString(),
                    new BoatView(
                            boatDoc.getBoatId().toHexString(),
                            boatDoc.getBoatName(),
                            boatDoc.getCapacity(),
                            boatDoc.getPricePerHour()
                    ),
                    e.getStartTime(),
                    e.getEndTime()
            );

            List<SpotFaunaView> faunaViews = Optional.ofNullable(s.getFauna()).orElseGet(List::of)
                    .stream()
                    .map(sf -> {
                        FishTypeDocument fishDoc = fishMap.get(sf.getFishId().toHexString());
                        return new SpotFaunaView(
                                new FishView(sf.getFishId().toHexString(), fishDoc != null ? fishDoc.getFishName() : "Unknown"),
                                sf.getSeasonality()
                        );
                    }).toList();

            FishingSpotView spotView = new FishingSpotView(
                    s.getSpotId().toHexString(),
                    s.getSpotName(),
                    s.getDepthMeters(),
                    s.getBottomType(),
                    (Set<SpotFaunaView>) faunaViews
            );

            return new BookingView(
                    b.getBookingId().toHexString(),
                    new UserView(user.getEmail()),
                    excursionView,
                    spotView
            );
        }).toList();

        model.addAttribute("bookings", viewList);
        return "admin/bookings";
    }

    @PostMapping("/{bookingId}/cancel")
    public String cancel(@PathVariable String bookingId) {
        bookingMongoService.cancelBookingByIdAdmin(bookingId);
        return "redirect:/admin/bookings?canceled";
    }
}
