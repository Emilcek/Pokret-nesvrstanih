package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.AnimalRepository;
import com.progi.WildTrack.domain.Animal;
import com.progi.WildTrack.dto.AnimalDTO;
import com.progi.WildTrack.dto.AnimalDetailsDTO;
import com.progi.WildTrack.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService {
    @Autowired
    private AnimalRepository animalRepo;

    @Override
    public AnimalDTO addAnimal(AnimalDTO animalDTO) {
        //if animal exists -> create new animal location with timestamp-
        if(!animalRepo.findByAnimalPhotoURLAndAnimalDescription(animalDTO.getAnimalPhotoURL(), animalDTO.getAnimalDescription()).isPresent()){
            Animal animal = Animal.builder()
                    .animalPhotoURL(animalDTO.getAnimalPhotoURL())
                    .animalDescription(animalDTO.getAnimalDescription())
                    .species(animalDTO.getAnimalSpecies())
                    .build();
            animalRepo.save(animal);
            return AnimalDTO.builder()
                    .animalPhotoURL(animal.getAnimalPhotoURL())
                    .animalDescription(animal.getAnimalDescription())
                    .animalSpecies(animal.getSpecies())
                    .build();
        } else {
            throw new RuntimeException("Animal already exists");
        }
    }

    @Override
    public List<AnimalDTO> getAllAnimals() {
        //find all animals
        //map each animal to animalDTO
        return animalRepo.findAll().stream().map(AnimalDTO :: new).toList();
    }


}
