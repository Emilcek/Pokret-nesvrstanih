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
import java.util.Random;

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
        if (animalLocation == null) {
            return ResponseEntity.badRequest().body("Animal location not found");
        }
        AnimalDetailsDTO animalDetailsDTO = AnimalDetailsDTO.builder()
                .animalId(animalLocation.getAnimal().getAnimalId())
                .animalSpecies(animalLocation.getAnimal().getSpecies())
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
            if (animalLocation != null) {
                AnimalDetailsDTO animalDetailsDTO = AnimalDetailsDTO.builder()
                        .animalId(animalLocation.getAnimal().getAnimalId())
                        .animalSpecies(animalLocation.getAnimal().getSpecies())
                        .animalDescription(animalLocation.getAnimal().getAnimalDescription())
                        .latitude(animalLocation.getLocationofAnimal().split(",")[0])
                        .longitude(animalLocation.getLocationofAnimal().split(",")[1])
                        .build();
                animalDetailsDTOList.add(animalDetailsDTO);
            }

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
        if (animalLocationList.isEmpty()) {
            return ResponseEntity.badRequest().body("Animal locations not found");
        }
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
            List<AnimalLocationDTO> animalLocationDTOList =  animalLocationRepo.findAllByAnimal_AnimalId(animal.getAnimalId())
                    .stream()
                    .map(animalLocation -> AnimalLocationDTO.builder()
                            .latitude(animalLocation.getLocationofAnimal().split(",")[0])
                            .longitude(animalLocation.getLocationofAnimal().split(",")[1])
                            .timestamp(animalLocation.getAnimalLocationTS().toString())
                            .build())
                    .toList();
            if (!animalLocationDTOList.isEmpty()) {
                AnimalAllLocationsDTO animalAllLocationsDTO = AnimalAllLocationsDTO.builder()
                        .animalId(animal.getAnimalId())
                        .animalSpecies(animal.getSpecies())
                        .animalDescription(animal.getAnimalDescription())
                        .animalLocations(animalLocationDTOList)
                        .build();
                animalAllLocationsDTOList.add(animalAllLocationsDTO);
            }

        });
        return ResponseEntity.ok(animalAllLocationsDTOList);
    }

    public void createAnimals() {
        if (!animalRepo.findAll().isEmpty()) {
            return;
        }
        Animal animal1 = Animal.builder()
                .animalDescription("Mrki smeđi medvjed sa crnim krznom oko očiju - mužjak")
                .species("Medvjed")
                .animalPhotoURL("")
                .build();
        Animal animal2 = Animal.builder()
                .animalDescription("Sivi vuk crnih oznaka na krznu - ženka")
                .species("Vuk")
                .animalPhotoURL("")
                .build();
        Animal animal3 = Animal.builder()
                .animalDescription("Obični jelen asimetričnih rogova - mužjak")
                .species("Jelen")
                .animalPhotoURL("")
                .build();
        Animal animal4 = Animal.builder()
                .animalDescription("Obični Ris - ženka")
                .species("Ris")
                .animalPhotoURL("")
                .build();
        Animal animal5 = Animal.builder()
                .animalDescription("Srnjak sa crnim oznakama na krznu - mužjak")
                .species("Srnjak")
                .animalPhotoURL("")
                .build();
        Animal animal6 = Animal.builder()
                .animalDescription("Divlja svinja s izraženim kljovama - ženka")
                .species("Divlja svinja")
                .animalPhotoURL("")
                .build();
        Animal animal7 = Animal.builder()
                .animalDescription("Bijela roda s ranjenim krilom - mužjak")
                .species("Bijela roda")
                .animalPhotoURL("")
                .build();
        Animal animal8 = Animal.builder()
                .animalDescription("Vjeverica s crvenim krznom - ženka")
                .species("Vjeverica")
                .animalPhotoURL("")
                .build();
        Animal animal9 = Animal.builder()
                .animalDescription("Poskok dužine 137 cm - mužjak")
                .species("Zmija")
                .animalPhotoURL("")
                .build();

        animalRepo.save(animal1);
        animalRepo.save(animal2);
        animalRepo.save(animal3);
        animalRepo.save(animal4);
        animalRepo.save(animal5);
        animalRepo.save(animal6);
        animalRepo.save(animal7);
        animalRepo.save(animal8);
        animalRepo.save(animal9);

        //add random locations for each animal
        addLocations(animal1, "45.815399", "15.966568");
        addLocations(animal2, "45.815399", "15.966568");
        addLocations(animal3, "45.815399", "15.966568");
        addLocations(animal4, "45.815399", "15.966568");
        addLocations(animal5, "45.815399", "15.966568");
        addLocations(animal6, "45.815399", "15.966568");
        addLocations(animal7, "45.815399", "15.966568");
        addLocations(animal8, "45.815399", "15.966568");
        addLocations(animal9, "45.815399", "15.966568");


    }

    public void addLocations(Animal animal, String latitude, String longitude) {
        //add random locations for animal
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        AnimalLocation animalLocation = AnimalLocation.builder()
                .animal(animal)
                .locationofAnimal(latitude + "," + longitude)
                .animalLocationTS(timestamp)
                .build();
        //save initial location
        animalLocationRepo.save(animalLocation);
        Random random = new Random();
        //random number of locations between 7 and 20
        int numberOfLocations = random.nextInt(14) + 7;
        for (int i = 0; i < numberOfLocations; i++) {
            //random delta latitude and longitude between -0.001 and 0.001
            double deltaLatitude = random.nextDouble() * 0.002 - 0.001;
            double deltaLongitude = random.nextDouble() * 0.002 - 0.001;
            double newLatitude = Double.parseDouble(latitude) + deltaLatitude;
            double newLongitude = Double.parseDouble(longitude) + deltaLongitude;
            //new timestamp is old timestamp + random number between 0.5 and 5 seconds
            Timestamp newTimestamp = new Timestamp(timestamp.getTime() + (long) ((random.nextDouble() * 4.5 + 0.5) * 1000));
            AnimalLocation newAnimalLocation = AnimalLocation.builder()
                    .animal(animal)
                    .locationofAnimal(String.valueOf(newLatitude) + "," + String.valueOf(newLongitude))
                    .animalLocationTS(newTimestamp)
                    .build();
            animalLocationRepo.save(newAnimalLocation);
            timestamp = newTimestamp;
            latitude = String.valueOf(newLatitude);
            longitude = String.valueOf(newLongitude);


        }
    }


}