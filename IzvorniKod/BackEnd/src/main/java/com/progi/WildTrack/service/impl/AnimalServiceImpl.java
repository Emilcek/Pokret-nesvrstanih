package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.AnimalRepository;
import com.progi.WildTrack.dao.TaskRepository;
import com.progi.WildTrack.domain.Animal;
import com.progi.WildTrack.dto.AnimalDTO;
import com.progi.WildTrack.service.AnimalService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService {
    @Autowired
    private AnimalRepository animalRepo;

    @Transactional
    @Override
    public ResponseEntity addAnimal(AnimalDTO animalDTO) {
        //if animal exists -> create new animal location with timestamp-
        if (animalRepo.findByAnimalPhotoURLAndAnimalDescription(animalDTO.getAnimalPhotoURL(), animalDTO.getAnimalDescription()).isPresent()) {
            return ResponseEntity.badRequest().body("Animal already exists");
        }
        Animal animal = Animal.builder()
                .animalPhotoURL(animalDTO.getAnimalPhotoURL())
                .animalDescription(animalDTO.getAnimalDescription())
                .species(animalDTO.getAnimalSpecies())
                .build();
        animalRepo.save(animal);
        return ResponseEntity.ok(animalDTO);

    }

    @Override
    public List<AnimalDTO> getAllAnimals() {
        //find all animals
        //map each animal to animalDTO
        return animalRepo.findAll().stream().map(AnimalDTO::new).toList();
    }


}
