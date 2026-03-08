package ru.rsreu.sea_fishing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsreu.sea_fishing.entities.Booking;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findByUserUserId(Integer userId);
}