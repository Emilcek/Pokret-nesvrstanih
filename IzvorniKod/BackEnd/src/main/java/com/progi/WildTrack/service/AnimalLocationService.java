package com.progi.WildTrack.service;

import com.progi.WildTrack.dto.AnimalDetailsDTO;
import com.progi.WildTrack.dto.AnimalLocationDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AnimalLocationService {
    ResponseEntity addAnimalLocation(Long animalId, AnimalLocationDTO animalLocationDTO);

    ResponseEntity getAnimalDetails(Long animalId);

    List<AnimalDetailsDTO> getAllAnimalsDetails();

    ResponseEntity getAnimalLocations(Long animalId);

    ResponseEntity getAllAnimalsAllLocations();
}
