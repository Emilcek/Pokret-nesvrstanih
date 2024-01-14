package com.progi.WildTrack.dao;

import com.progi.WildTrack.domain.AnimalLocation;
import com.progi.WildTrack.dto.AnimalDTO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalLocationRepository extends JpaRepository<AnimalLocation,String> {
    List<AnimalLocation> findAllByAnimal_AnimalId(Long animalId);
    boolean existsByAnimal_AnimalId(Long animalId);
    AnimalLocation findFirstByAnimal_AnimalIdOrderByAnimalLocationTSDesc(Long animalId);

}
