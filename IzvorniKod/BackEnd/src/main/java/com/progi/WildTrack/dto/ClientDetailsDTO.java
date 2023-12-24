package com.progi.WildTrack.dto;

import com.progi.WildTrack.domain.Client;
import com.progi.WildTrack.domain.Station;
import com.progi.WildTrack.domain.StationLead;
import com.progi.WildTrack.domain.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDetailsDTO {

    private String clientName;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private byte[] clientPhoto;
    private Set<String> educatedFor = new HashSet<>();
    private String stationName;

    public ClientDetailsDTO(Client client) {
        SetBaseAttributes(client);
    }

    public ClientDetailsDTO(Client client, Set<Vehicle> educatedFor) {
        System.out.println("ClientDetailsDTO");
        SetBaseAttributes(client);
        for (Vehicle vehicle : educatedFor) {
            this.educatedFor.add(vehicle.getVehicleType());
        }
    }

    public ClientDetailsDTO(Client client, StationLead stationLead) {
        SetBaseAttributes(client);
        if (stationLead.getStation() != null) {
            this.stationName = stationLead.getStation().getStationName();
        }
        else {
            this.stationName = null;
        }
    }


    private void SetBaseAttributes(Client client) {
        this.clientName = client.getClientName();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.role = client.getRole();
        this.clientPhoto = client.getClientPhoto();
    }

}


