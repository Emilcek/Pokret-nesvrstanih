package com.progi.WildTrack.dto;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimalLocationDTO {
    private String longitude;
    private String latitude;
    private String timestamp;
}
