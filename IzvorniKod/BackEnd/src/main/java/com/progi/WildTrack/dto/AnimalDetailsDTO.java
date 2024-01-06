package com.progi.WildTrack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDetailsDTO {
    private Long animalId;
    private String animalSpecies;
    private String animalPhotoURL;
    private String animalDescription;
    private String longitude;
    private String latitude;
    //private String timestamp;
}
