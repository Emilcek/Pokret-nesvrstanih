package com.progi.WildTrack.service;

import com.progi.WildTrack.dto.AnimalDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AnimalService {
    ResponseEntity addAnimal(AnimalDTO animalDTO);


    List<AnimalDTO> getAllAnimals();
}
