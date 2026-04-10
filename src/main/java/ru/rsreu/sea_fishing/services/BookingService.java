package ru.rsreu.sea_fishing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.rsreu.sea_fishing.entities.Booking;
import ru.rsreu.sea_fishing.entities.Excursion;
import ru.rsreu.sea_fishing.entities.FishingSpot;
import ru.rsreu.sea_fishing.entities.User;
import ru.rsreu.sea_fishing.repositories.BookingRepository;
import ru.rsreu.sea_fishing.repositories.ExcursionRepository;
import ru.rsreu.sea_fishing.repositories.FishingSpotRepository;
import ru.rsreu.sea_fishing.repositories.UserRepository;

import java.util.List;

@Profile("sql")
@Service
@RequiredArgsConstructor
public class BookingService {

    private final ExcursionRepository excursionRepository;
    private final FishingSpotRepository spotRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public List<Excursion> getAllExcursions() {
        return excursionRepository.findAll();
    }

    public List<FishingSpot> getAllSpots() {
        return spotRepository.findAll();
    }

    public void saveBooking(User user, Integer excursionId, Integer spotId) {
        Excursion excursion = excursionRepository.findById(Long.valueOf(excursionId))
                .orElseThrow(() -> new RuntimeException("Экскурсия не найдена"));

        FishingSpot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new RuntimeException("Место не найдено"));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setExcursion(excursion);
        booking.setSpot(spot);

        bookingRepository.save(booking);
    }

    public List<Booking> getBookingsForCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return bookingRepository.findAllByUser(user);
    }

    public void cancelBooking(Integer bookingId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Бронирование не найдено"));

        if (!booking.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Недостаточно прав для отмены этого бронирования");
        }

        bookingRepository.delete(booking);
    }

    public List<Booking> getAllBookingsForAdmin() {
        return bookingRepository.findAll();
    }

    public void cancelBookingById(Integer bookingId) {
        bookingRepository.deleteById(bookingId);
    }
}
