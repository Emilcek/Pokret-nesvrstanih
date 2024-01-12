package com.progi.WildTrack.dto;


import com.progi.WildTrack.domain.AnimalLocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimalAllLocationsDTO {
    private Long animalId;
    private String animalSpecies;
    //private String animalPhotoURL;
    private String animalDescription;
    private List<AnimalLocationDTO> animalLocations;
}
