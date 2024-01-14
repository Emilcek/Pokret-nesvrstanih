package com.progi.WildTrack.dao;

import com.progi.WildTrack.domain.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    Optional<Animal> findAnimalByAnimalDescriptionAndSpecies (String animalDescription, String animalSpecies);
}
