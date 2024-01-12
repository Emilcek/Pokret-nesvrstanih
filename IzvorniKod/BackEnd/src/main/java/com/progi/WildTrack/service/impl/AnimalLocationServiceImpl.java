package com.progi.WildTrack.service.impl;

import com.progi.WildTrack.dao.AnimalLocationRepository;
import com.progi.WildTrack.dao.AnimalRepository;
import com.progi.WildTrack.domain.Animal;
import com.progi.WildTrack.domain.AnimalLocation;
import com.progi.WildTrack.dto.AnimalAllLocationsDTO;
import com.progi.WildTrack.dto.AnimalDetailsDTO;
import com.progi.WildTrack.dto.AnimalLocationDTO;
import com.progi.WildTrack.service.AnimalLocationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class AnimalLocationServiceImpl implements AnimalLocationService {
    @Autowired
    private AnimalLocationRepository animalLocationRepo;
    @Autowired
    private AnimalRepository animalRepo;

    @Override
    public ResponseEntity addAnimalLocation(Long animalId, AnimalLocationDTO animalLocationDTO) {
        //if animal exists -> create new animal location with timestamp
        if (!animalRepo.existsById(animalId)) {
            return ResponseEntity.badRequest().body("Animal not found");
        }
        Animal animal = animalRepo.findById(animalId).get();
        String location = animalLocationDTO.getLatitude() + "," + animalLocationDTO.getLongitude();
        AnimalLocation animalLocation = AnimalLocation.builder()
                .animal(animal)
                .locationofAnimal(location)
                .animalLocationTS(Timestamp.valueOf(animalLocationDTO.getTimestamp()))
                .build();
        animalLocationRepo.save(animalLocation);
        System.out.println("Animal location added " + animalLocation);
        return ResponseEntity.ok(animalLocationDTO);
    }

    @Transactional
    @Override
    public ResponseEntity getAnimalLocation(Long animalId) {
        //if animal exists -> get animal last location and animal details
        if (!animalRepo.existsById(animalId)) {
            return ResponseEntity.badRequest().body("Animal not found");
        }
        AnimalLocation animalLocation = animalLocationRepo.findFirstByAnimal_AnimalIdOrderByAnimalLocationTSDesc(animalId);
        AnimalDetailsDTO animalDetailsDTO = AnimalDetailsDTO.builder()
                .animalId(animalLocation.getAnimal().getAnimalId())
                .animalSpecies(animalLocation.getAnimal().getSpecies())
                //.animalPhotoURL(animalLocation.getAnimal().getAnimalPhotoURL())
                .animalDescription(animalLocation.getAnimal().getAnimalDescription())
                .latitude(animalLocation.getLocationofAnimal().split(",")[0])
                .longitude(animalLocation.getLocationofAnimal().split(",")[1])
                .build();
        return ResponseEntity.ok(animalDetailsDTO);
    }

        @Override
        public List<AnimalDetailsDTO> getAllAnimalsCurrentLocations() {
            //for each animal get last location and animal details
            List<AnimalDetailsDTO> animalDetailsDTOList = new java.util.ArrayList<>();
            var animals = animalRepo.findAll();
           animals.forEach(animal -> {
               AnimalLocation animalLocation = animalLocationRepo.findFirstByAnimal_AnimalIdOrderByAnimalLocationTSDesc(animal.getAnimalId());
               AnimalDetailsDTO animalDetailsDTO = AnimalDetailsDTO.builder()
                       .animalId(animalLocation.getAnimal().getAnimalId())
                       .animalSpecies(animalLocation.getAnimal().getSpecies())
                       //.animalPhotoURL(animalLocation.getAnimal().getAnimalPhotoURL())
                       .animalDescription(animalLocation.getAnimal().getAnimalDescription())
                       .latitude(animalLocation.getLocationofAnimal().split(",")[0])
                       .longitude(animalLocation.getLocationofAnimal().split(",")[1])
                       .build();
               animalDetailsDTOList.add(animalDetailsDTO);
           });
            return animalDetailsDTOList;
        }

    @Override
    public ResponseEntity getAnimalLocations(Long animalId) {
        //if animal exists -> get all animal locations
        Animal animal = animalRepo.findById(animalId).get();
        if (animal == null) {
            return ResponseEntity.badRequest().body("Animal does not exist");
        }
        List<AnimalLocation> animalLocationList = animalLocationRepo.findAllByAnimal_AnimalId(animalId);
        List<AnimalLocationDTO> animalLocationDTOList = new java.util.ArrayList<>();
        animalLocationList.forEach(animalLocation -> {
            AnimalLocationDTO animalLocationDTO = AnimalLocationDTO.builder()
                    .latitude(animalLocation.getLocationofAnimal().split(",")[0])
                    .longitude(animalLocation.getLocationofAnimal().split(",")[1])
                    .timestamp(animalLocation.getAnimalLocationTS().toString())
                    .build();
            animalLocationDTOList.add(animalLocationDTO);
        });
        return ResponseEntity.ok(animalLocationDTOList);
    }

    @Override
    public ResponseEntity getAllAnimalsAllLocations() {
        //for each animal get all locations
        var animals = animalRepo.findAll();
        List<AnimalAllLocationsDTO> animalAllLocationsDTOList = new java.util.ArrayList<>();
        animals.forEach(animal -> {
            AnimalAllLocationsDTO animalAllLocationsDTO = AnimalAllLocationsDTO.builder()
                    .animalId(animal.getAnimalId())
                    .animalSpecies(animal.getSpecies())
                    //.animalPhotoURL(animal.getAnimalPhotoURL())
                    .animalDescription(animal.getAnimalDescription())
                    .animalLocations(
                            animalLocationRepo.findAllByAnimal_AnimalId(animal.getAnimalId())
                                    .stream()
                                    .map(animalLocation -> AnimalLocationDTO.builder()
                                            .latitude(animalLocation.getLocationofAnimal().split(",")[0])
                                            .longitude(animalLocation.getLocationofAnimal().split(",")[1])
                                            .timestamp(animalLocation.getAnimalLocationTS().toString())
                                            .build())
                                    .toList()
                    )
                    .build();
        });
        return ResponseEntity.ok(animalAllLocationsDTOList);
    }


}