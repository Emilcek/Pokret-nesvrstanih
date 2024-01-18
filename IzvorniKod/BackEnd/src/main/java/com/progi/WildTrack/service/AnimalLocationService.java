package com.progi.WildTrack.service;

import com.progi.WildTrack.domain.Animal;
import com.progi.WildTrack.dto.AnimalDetailsDTO;
import com.progi.WildTrack.dto.AnimalLocationDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AnimalLocationService {
    ResponseEntity addAnimalLocation(Long animalId, AnimalLocationDTO animalLocationDTO);

    ResponseEntity getAnimalLocation(Long animalId);

    List<AnimalDetailsDTO> getAllAnimalsCurrentLocations();

    ResponseEntity getAnimalLocations(Long animalId);

    ResponseEntity getAllAnimalsAllLocations();

     void createAnimals();

     void addLocations(Animal animal, String latitude, String longitude);
}
