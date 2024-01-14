package com.progi.WildTrack.dto;


import com.progi.WildTrack.domain.Animal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDTO {
    private Long animalId;
    private String animalSpecies;
    //private String animalPhotoURL;
    private String animalDescription;

    public AnimalDTO(Animal animal) {
        this.animalId = animal.getAnimalId();
        this.animalSpecies = animal.getSpecies();
       // this.animalPhotoURL = animal.getAnimalPhotoURL();
        this.animalDescription = animal.getAnimalDescription();
    }
}
