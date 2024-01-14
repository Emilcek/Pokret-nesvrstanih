package com.progi.WildTrack.controllers;


import com.progi.WildTrack.dto.AnimalLocationDTO;
import com.progi.WildTrack.service.AnimalLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/animallocation")
@CrossOrigin(origins = "${FRONTEND_API_URL}")
@RequiredArgsConstructor
public class AnimalGPSController {
    private final AnimalLocationService animalLocationService;

    //animal's current location is added to the database
    @PostMapping("/{animalId}")
    public ResponseEntity addAnimalLocation(@PathVariable Long animalId, @RequestBody AnimalLocationDTO animalLocationDTO) {
        return animalLocationService.addAnimalLocation(animalId, animalLocationDTO);
    }
}
