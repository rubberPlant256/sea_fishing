package ru.rsreu.sea_fishing.mongo.services;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.rsreu.sea_fishing.dto.BoatFormDto;
import ru.rsreu.sea_fishing.mongo.document.BoatDocument;
import ru.rsreu.sea_fishing.mongo.repository.BoatMongoRepository;

import java.util.List;

@Profile("mongo")
@Service
@RequiredArgsConstructor
public class BoatMongoService {

    private final BoatMongoRepository boatMongoRepository;

    public List<BoatDocument> findAll() {
        return boatMongoRepository.findAll();
    }

    public void save(BoatFormDto dto) {
        BoatDocument doc = new BoatDocument();

        if (dto.boatId() != null && !dto.boatId().isBlank()) {
            doc.setBoatId(new ObjectId(dto.boatId()));
        }
        doc.setBoatName(dto.boatName());
        doc.setCapacity(dto.capacity());
        doc.setPricePerHour(dto.pricePerHour());

        boatMongoRepository.save(doc);
    }

    public void deleteById(String boatId) {
        boatMongoRepository.deleteById(new ObjectId(boatId));
    }
}
