package ru.rsreu.sea_fishing.mongo.services;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.rsreu.sea_fishing.dto.BookingFormDto;
import ru.rsreu.sea_fishing.mongo.document.BookingDocument;
import ru.rsreu.sea_fishing.mongo.document.ExcursionDocument;
import ru.rsreu.sea_fishing.mongo.document.FishingSpotDocument;
import ru.rsreu.sea_fishing.mongo.document.UserDocument;
import ru.rsreu.sea_fishing.mongo.repository.BookingMongoRepository;
import ru.rsreu.sea_fishing.mongo.repository.ExcursionMongoRepository;
import ru.rsreu.sea_fishing.mongo.repository.FishingSpotMongoRepository;
import ru.rsreu.sea_fishing.mongo.repository.UserMongoRepository;

import java.util.List;

@Profile("mongo")
@Service
@RequiredArgsConstructor
public class BookingMongoService {

    private final BookingMongoRepository bookingMongoRepository;
    private final ExcursionMongoRepository excursionMongoRepository;
    private final FishingSpotMongoRepository fishingSpotMongoRepository;
    private final UserMongoRepository userMongoRepository;

    public List<BookingDocument> getBookingsForCurrentUser(String email) {
        UserDocument user = userMongoRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        return bookingMongoRepository.findAllByUserId(user.getUserId());
    }

    public void saveBooking(String email, BookingFormDto form) {
        UserDocument user = userMongoRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        ExcursionDocument excursion = excursionMongoRepository.findById(new ObjectId(form.excursionId()))
                .orElseThrow(() -> new RuntimeException("Экскурсия не найдена"));

        FishingSpotDocument spot = fishingSpotMongoRepository.findById(new ObjectId(form.spotId()))
                .orElseThrow(() -> new RuntimeException("Место не найдено"));

        BookingDocument booking = new BookingDocument();
        booking.setUserId(user.getUserId());
        booking.setExcursionId(excursion.getExcursionId());
        booking.setSpotId(spot.getSpotId());

        bookingMongoRepository.save(booking);
    }

    public void cancelBookingById(String bookingId, String email) {
        UserDocument user = userMongoRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        BookingDocument booking = bookingMongoRepository.findById(new ObjectId(bookingId))
                .orElseThrow(() -> new RuntimeException("Бронирование не найдено"));

        if (!booking.getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Недостаточно прав для отмены этого бронирования");
        }

        bookingMongoRepository.delete(booking);
    }

    public List<BookingDocument> getAllBookingsForAdmin() {
        return bookingMongoRepository.findAll();
    }

    public void cancelBookingByIdAdmin(String bookingId) {
        bookingMongoRepository.deleteById(new ObjectId(bookingId));
    }
}
