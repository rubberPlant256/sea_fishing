package ru.rsreu.sea_fishing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.rsreu.sea_fishing.entities.Excursion;
import ru.rsreu.sea_fishing.repositories.ExcursionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Profile("sql")
@Service
@RequiredArgsConstructor
public class ExcursionService {

    private final ExcursionRepository excursionRepository;

    public List<Excursion> findAllFromDate() {
        LocalDateTime now = LocalDateTime.now();
        return excursionRepository.findByStartTimeAfterOrderByStartTimeAsc(now);
    }

    public void deleteById(Long id) {
        excursionRepository.deleteById(id);
    }

    public void save(Excursion excursion) {
        excursionRepository.save(excursion);
    }
}
