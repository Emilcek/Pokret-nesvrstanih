package com.progi.WildTrack.controllers;


import com.progi.WildTrack.dto.AnimalDTO;
import com.progi.WildTrack.dto.AnimalDetailsDTO;
import com.progi.WildTrack.dto.AnimalLocationDTO;
import com.progi.WildTrack.service.AnimalLocationService;
import com.progi.WildTrack.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animal")
@CrossOrigin(origins = "${FRONTEND_API_URL}")
@RequiredArgsConstructor
public class AnimalController {

    private final  AnimalLocationService animalLocationService;
    private final AnimalService animalService;

    //list of all animals
    @GetMapping("/all")
    public ResponseEntity<List<AnimalDTO>> getAllAnimals() {
        return ResponseEntity.ok(animalService.getAllAnimals());
    }
    //animal is added to the database
    @PostMapping("/add")
    public ResponseEntity addAnimal(@RequestBody AnimalDTO animal) {
        return animalService.addAnimal(animal);
    }

    //current location of the animal
    @GetMapping("/location/get/{animalId}")
    public ResponseEntity getAnimalLocation(@PathVariable Long animalId) {
        return animalLocationService.getAnimalLocation(animalId);
    }
    //get all locations of the animal
    @GetMapping("/locations/all/{animalId}")
    public ResponseEntity getAnimalLocations(@PathVariable Long animalId) {
        return animalLocationService.getAnimalLocations(animalId);
    }

    //list of all animals current location
    @GetMapping("/currentLocations/all")
    public ResponseEntity<List<AnimalDetailsDTO>> getAllAnimalsCurrentLocations() {
        return ResponseEntity.ok(animalLocationService.getAllAnimalsCurrentLocations());
    }
    //list of all animals location history
    @GetMapping("locations/all")
    public ResponseEntity getAllAnimalsAllLocations() {
        return animalLocationService.getAllAnimalsAllLocations();
    }


}
