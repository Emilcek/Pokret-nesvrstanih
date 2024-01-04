package com.progi.WildTrack.service;

import com.progi.WildTrack.dto.AnimalDetailsDTO;
import com.progi.WildTrack.dto.AnimalLocationDTO;

import java.util.List;

public interface AnimalLocationService {
    String addAnimalLocation(Long animalId, AnimalLocationDTO animalLocationDTO);

    AnimalDetailsDTO getAnimalDetails(Long animalId);

    List<AnimalDetailsDTO> getAllAnimalsDetails();
}
