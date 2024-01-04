package com.progi.WildTrack.service;

import com.progi.WildTrack.dto.AnimalDTO;
import com.progi.WildTrack.dto.AnimalDetailsDTO;

import java.util.List;

public interface AnimalService {
    AnimalDTO addAnimal(AnimalDTO animalDTO);


    List<AnimalDTO> getAllAnimals();
}
