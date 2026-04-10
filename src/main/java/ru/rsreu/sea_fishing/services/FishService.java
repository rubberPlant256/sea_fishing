package ru.rsreu.sea_fishing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.rsreu.sea_fishing.entities.FishType;
import ru.rsreu.sea_fishing.repositories.FishTypeRepository;

import java.util.List;

@Profile("sql")
@Service
@RequiredArgsConstructor
public class FishService {

    private final FishTypeRepository fishTypeRepository;

    public List<FishType> findAll() {
        return fishTypeRepository.findAllByOrderByFishNameAsc();
    }

    public void save(FishType fishType) {
        fishTypeRepository.save(fishType);
    }

    public void deleteById(Integer id) {
        fishTypeRepository.deleteById(id);
    }
}
