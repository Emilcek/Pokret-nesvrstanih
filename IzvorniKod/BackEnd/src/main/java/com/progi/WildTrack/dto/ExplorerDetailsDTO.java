package com.progi.WildTrack.dto;

import com.progi.WildTrack.domain.Description;
import com.progi.WildTrack.domain.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Set;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExplorerDetailsDTO {
    private String explorerName;
    private String firstName;
    private String lastName;
    private String email;
    private byte[] clientPhoto;
    private Set<Vehicle> educatedFor;
    private String stationName;
    private String status;
    private String longitude;
    private String latitude;
}
