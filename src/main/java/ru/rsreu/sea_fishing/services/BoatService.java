package ru.rsreu.sea_fishing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.rsreu.sea_fishing.entities.Boat;
import ru.rsreu.sea_fishing.repositories.BoatRepository;

import java.util.List;

@Profile("sql")
@Service
@RequiredArgsConstructor
public class BoatService {

    private final BoatRepository boatRepository;

    public List<Boat> findAll() {
        return boatRepository.findAll();
    }

    public void save(Boat boat) {
        boatRepository.save(boat);
    }

    public void deleteById(Integer id) {
        boatRepository.deleteById(id);
    }

    public Boat findById(Integer id) {
        return boatRepository.findById(id).orElseThrow(() -> new RuntimeException("Лодка не найдена"));
    }

}
